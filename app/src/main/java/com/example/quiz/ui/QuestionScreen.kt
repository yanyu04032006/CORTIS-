package com.example.quiz.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.model.QuizOption
import com.example.quiz.model.QuizQuestion
import com.example.quiz.ui.components.DoodleStar
import com.example.quiz.ui.components.StickerTag
import com.example.quiz.util.SoundEffectManager

@Composable
fun QuestionScreen(
    question: QuizQuestion,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    selectedOption: QuizOption?,
    onOptionSelected: (QuizOption) -> Unit,
    onPreviousQuestion: () -> Unit,
    onNextQuestion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = (currentQuestionIndex + 1).toFloat() / totalQuestions.toFloat()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFFFEEF3) // 溫柔浪漫淡粉色背景
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 520.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // 上半部：標題導航、進度條與題目
                Column(modifier = Modifier.fillMaxWidth()) {
                    // 頂部導航行
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (currentQuestionIndex > 0) {
                            IconButton(
                                onClick = onPreviousQuestion,
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(Color.White, CircleShape)
                                    .border(1.5.dp, Color(0xFF1F1F24), CircleShape)
                                    .testTag("prev_question_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "上一題",
                                    tint = Color(0xFF1F1F24),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(42.dp))
                        }

                        // 題號手寫標籤
                        StickerTag(
                            text = "第 ${currentQuestionIndex + 1} / $totalQuestions 題",
                            backgroundColor = Color(0xFFFFD54F),
                            textColor = Color(0xFF1F1F24),
                            rotation = 1f
                        )

                        DoodleStar(size = 26.dp)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 進度條 (青春街頭風格進度條)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "心動雷達掃描進度",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5A5662)
                            )
                            // 視覺化符號進度條（例如 ━━━━━━░░░░）
                            val filledBars = ((currentQuestionIndex + 1) * 10 / totalQuestions).coerceIn(1, 10)
                            val emptyBars = 10 - filledBars
                            val barText = "━".repeat(filledBars) + "░".repeat(emptyBars)
                            Text(
                                text = barText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFFF2E63),
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.5.dp, Color(0xFF1F1F24), RoundedCornerShape(8.dp)),
                            color = Color(0xFFFF2E63),
                            trackColor = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 題目卡片
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(20.dp))
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .border(2.dp, Color(0xFF1F1F24), RoundedCornerShape(20.dp))
                            .padding(horizontal = 20.dp, vertical = 22.dp)
                    ) {
                        Text(
                            text = question.prompt,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1A191E),
                            lineHeight = 26.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // 5 個大型選項卡片
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        question.options.forEach { option ->
                            val isSelected = selectedOption?.letter == option.letter
                            OptionCard(
                                option = option,
                                isSelected = isSelected,
                                onClick = {
                                    SoundEffectManager.playOptionClickSound()
                                    onOptionSelected(option)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 下一步 / 查看結果 按鈕
                Button(
                    onClick = {
                        SoundEffectManager.playActionSound()
                        onNextQuestion()
                    },
                    enabled = selectedOption != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .shadow(if (selectedOption != null) 4.dp else 0.dp, RoundedCornerShape(28.dp))
                        .testTag("next_question_button"),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1F1F24),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFD6D1DA),
                        disabledContentColor = Color(0xFF8C8694)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (currentQuestionIndex == totalQuestions - 1) "查看測驗結果 ✦" else "下一題",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "下一題箭頭",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun OptionCard(
    option: QuizOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1.0f,
        animationSpec = spring(dampingRatio = 0.6f),
        label = "optionScale"
    )

    val bgColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFFFEEF3) else Color.White,
        label = "bgColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFFF2E63) else Color(0xFF1F1F24),
        label = "borderColor"
    )

    val borderWidth = if (isSelected) 2.5.dp else 1.5.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .shadow(if (isSelected) 5.dp else 2.dp, RoundedCornerShape(18.dp))
            .background(bgColor, RoundedCornerShape(18.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag("option_${option.letter}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 選項字母標籤 A / B / C / D / E
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        if (isSelected) Color(0xFFFF2E63) else Color(0xFFF0EDF2),
                        CircleShape
                    )
                    .border(1.5.dp, Color(0xFF1F1F24), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "已選中",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = option.letter,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1F1F24)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // 選項文案
            Text(
                text = option.text,
                fontSize = 15.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFF1F1F24) else Color(0xFF2C2B30),
                lineHeight = 22.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
