package com.example.week5vp.Soal2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week5vp.Soal2.model.AnswerHistory
import com.example.week5vp.Soal2.model.Question

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(
            "Ibu Kota Di Indonesia?",
            listOf("Surabaya", "Bandung", "Jakarta", "Medan"),
            "Jakarta"
        ),
        Question(
            "Apakah Mata Kuliah VP susah?",
            listOf("Sulit", "Sangat Mudah", "Mustahil", "Entahlah"),
            "Sangat Mudah"
        ),
        Question(
            "Natal dirayakan pada bulan?",
            listOf("Agustus", "Oktober", "Desember", "Januari"),
            "Desember"
        ),
        Question(
            "Bahasa pemrograman resmi untuk Jetpack Compose?",
            listOf("Java", "Kotlin", "Dart", "Swift"),
            "Kotlin"
        ),
        Question(
            "Berapa hasil dari 7 x 8?",
            listOf("54", "56", "58", "64"),
            "56"
        )
    )
    val maxQuestions = 5
    var countInput by mutableStateOf("")
    var listQuiz by mutableStateOf<List<Question>>(emptyList())
    var answers by mutableStateOf<List<AnswerHistory>>(emptyList())
    var selectedAnswer by mutableStateOf<String?>(null)

    val countError: String?
        get() {
            if (countInput.isBlank()) return null
            val number = countInput.toIntOrNull() ?: return "Enter a valid number"
            return when {
                number > maxQuestions -> "Maximum is $maxQuestions questions"
                number < 1 -> "Minimum is 1 question"
                else -> null
            }
        }

    val canStart: Boolean
        get() = countInput.isNotBlank() && countError == null

    val score: Int
        get() = answers.count { it.isCorrect }

    fun startQuiz() {
        listQuiz = questionBank.shuffled().take(countInput.toInt())
    }

    fun nextQuestion() {
        val selected = selectedAnswer ?: return
        answers = answers + AnswerHistory(listQuiz[answers.size], selected)
        selectedAnswer = null
    }

    fun restartQuiz() {
        countInput = ""
        listQuiz = emptyList()
        answers = emptyList()
    }
}