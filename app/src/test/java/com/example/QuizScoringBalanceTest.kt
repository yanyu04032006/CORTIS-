package com.example

import com.example.quiz.model.CortisMember
import com.example.quiz.model.QuizCalculator
import com.example.quiz.model.QuizRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class QuizScoringBalanceTest {

    @Test
    fun testQuestionsAndOptionsCount() {
        assertEquals(10, QuizRepository.questions.size)
        QuizRepository.questions.forEachIndexed { qIdx, question ->
            assertEquals("Question ${qIdx + 1} must have 5 options", 5, question.options.size)
            question.options.forEachIndexed { oIdx, opt ->
                val w = opt.weights
                val nonZeroCount = listOf(w.james, w.juhoon, w.martin, w.seonghyeon, w.keonho).count { it > 0 }
                assertTrue("Q${qIdx + 1} option ${opt.letter} should affect at least 3 members, got $nonZeroCount", nonZeroCount >= 3)
            }
        }
    }

    @Test
    fun testSimulation10000RandomRuns() {
        val winCounts = mutableMapOf<CortisMember, Int>()
        CortisMember.values().forEach { winCounts[it] = 0 }

        var dualBiasCount = 0
        val runs = 10000
        val random = Random(42)

        for (i in 0 until runs) {
            val answers = QuizRepository.questions.associate { question ->
                val chosenOption = question.options[random.nextInt(question.options.size)]
                question.id to chosenOption
            }

            val result = QuizCalculator.calculateResult(answers)
            winCounts[result.primaryMember] = (winCounts[result.primaryMember] ?: 0) + 1
            if (result.isDualBias) {
                dualBiasCount++
            }
        }

        println("=== 10,000 Simulation Win Rates ===")
        winCounts.forEach { (member, count) ->
            val pct = count.toDouble() / runs * 100.0
            println("${member.fullName}: $count ($pct %)")
            // Every member should achieve between 10% and 35% win rate in uniform random simulations
            assertTrue("${member.englishName} win rate ($pct%) is too low (< 10%)", pct >= 10.0)
            assertTrue("${member.englishName} win rate ($pct%) is too high (> 35%)", pct <= 35.0)
        }

        val dualPct = dualBiasCount.toDouble() / runs * 100.0
        println("Dual bias trigger count: $dualBiasCount ($dualPct %)")
        assertTrue("Dual bias trigger rate should be realistic (15% ~ 55%), got $dualPct%", dualPct in 15.0..55.0)
    }

    @Test
    fun testShuffledQuestions() {
        val shuffled = QuizRepository.getShuffledQuestions()
        assertEquals(10, shuffled.size)
        shuffled.forEach { q ->
            assertEquals(5, q.options.size)
            assertEquals(listOf("A", "B", "C", "D", "E"), q.options.map { it.letter })
        }
    }
}
