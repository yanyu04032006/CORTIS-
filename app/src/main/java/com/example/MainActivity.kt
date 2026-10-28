package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.quiz.model.QuizCalculator
import com.example.quiz.model.QuizOption
import com.example.quiz.model.QuizRepository
import com.example.quiz.model.QuizResult
import com.example.quiz.ui.CoverScreen
import com.example.quiz.ui.QuestionScreen
import com.example.quiz.ui.RadarLoadingScreen
import com.example.quiz.ui.ResultScreen
import com.example.ui.theme.PastelPinkBg
import com.example.quiz.util.SoundEffectManager
import com.example.ui.theme.MyApplicationTheme

enum class QuizFlowState {
    COVER,
    QUESTION,
    LOADING,
    RESULT
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoundEffectManager.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CortisQuizApp()
            }
        }
    }
}

@Composable
fun CortisQuizApp() {
    var flowState by remember { mutableStateOf(QuizFlowState.COVER) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val selectedAnswers = remember { mutableStateMapOf<Int, QuizOption>() }
    var quizResult by remember { mutableStateOf<QuizResult?>(null) }

    var questions by remember { mutableStateOf(QuizRepository.getShuffledQuestions()) }
    val totalQuestions = questions.size

    // 返回鍵處理
    BackHandler(enabled = flowState != QuizFlowState.COVER) {
        when (flowState) {
            QuizFlowState.QUESTION -> {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--
                } else {
                    flowState = QuizFlowState.COVER
                }
            }
            QuizFlowState.RESULT -> {
                flowState = QuizFlowState.COVER
            }
            QuizFlowState.LOADING -> {
                // 不在中途打斷動畫
            }
            QuizFlowState.COVER -> {
                // 預設行為
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PastelPinkBg
    ) { innerPadding ->
        AnimatedContent(
            targetState = flowState,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "quizScreenTransition",
            modifier = Modifier.padding(innerPadding)
        ) { state ->
            when (state) {
                QuizFlowState.COVER -> {
                    CoverScreen(
                        onStartQuiz = {
                            SoundEffectManager.playActionSound()
                            selectedAnswers.clear()
                            currentQuestionIndex = 0
                            quizResult = null
                            questions = QuizRepository.getShuffledQuestions()
                            flowState = QuizFlowState.QUESTION
                        }
                    )
                }

                QuizFlowState.QUESTION -> {
                    val currentQuestion = questions[currentQuestionIndex]
                    val currentSelectedOption = selectedAnswers[currentQuestionIndex]

                    QuestionScreen(
                        question = currentQuestion,
                        currentQuestionIndex = currentQuestionIndex,
                        totalQuestions = totalQuestions,
                        selectedOption = currentSelectedOption,
                        onOptionSelected = { option ->
                            selectedAnswers[currentQuestionIndex] = option
                        },
                        onPreviousQuestion = {
                            if (currentQuestionIndex > 0) {
                                currentQuestionIndex--
                            } else {
                                flowState = QuizFlowState.COVER
                            }
                        },
                        onNextQuestion = {
                            if (currentQuestionIndex < totalQuestions - 1) {
                                currentQuestionIndex++
                            } else {
                                // 10 題皆回答完成，計算結果並進入雷達載入動畫
                                quizResult = QuizCalculator.calculateResult(selectedAnswers)
                                flowState = QuizFlowState.LOADING
                            }
                        }
                    )
                }

                QuizFlowState.LOADING -> {
                    RadarLoadingScreen(
                        onComplete = {
                            SoundEffectManager.playResultCelebrationSound()
                            flowState = QuizFlowState.RESULT
                        }
                    )
                }

                QuizFlowState.RESULT -> {
                    quizResult?.let { result ->
                        ResultScreen(
                            result = result,
                            onRetest = {
                                SoundEffectManager.playActionSound()
                                selectedAnswers.clear()
                                currentQuestionIndex = 0
                                quizResult = null
                                questions = QuizRepository.getShuffledQuestions()
                                flowState = QuizFlowState.COVER
                            }
                        )
                    }
                }
            }
        }
    }
}
