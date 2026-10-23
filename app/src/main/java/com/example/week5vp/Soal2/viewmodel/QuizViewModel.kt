package com.example.practicerabu.q2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.week5vp.Soal2.model.AnswerHistory
import com.example.week5vp.Soal2.model.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
    private val _countInput = MutableStateFlow("")
    val countInput: StateFlow<String> = _countInput.asStateFlow()
    private val _quizQuestions = MutableStateFlow<List<Question>>(emptyList())
    val quizQuestions: StateFlow<List<Question>> = _quizQuestions.asStateFlow()
    private val _answers = MutableStateFlow<List<AnswerHistory>>(emptyList())
    val answers: StateFlow<List<AnswerHistory>> = _answers.asStateFlow()
    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer.asStateFlow()
    fun countError(): String? {
        val text = _countInput.value
        if (text.isBlank()) return null
        val number = text.toIntOrNull() ?: return "Enter a valid number"
        return when {
            number > maxQuestions -> "Maximum is $maxQuestions questions"
            number < 1 -> "Minimum is 1 question"
            else -> null
        }
    }
    fun canStart(): Boolean {
        return _countInput.value.isNotBlank() && countError() == null
    }

    fun score(): Int {
        return _answers.value.count { it.isCorrect }
    }

    fun onCountChange(text: String) {
        _countInput.value = text.filter { it.isDigit() }
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun startQuiz() {
        if (!canStart()) return
        _quizQuestions.value = questionBank.shuffled().take(_countInput.value.toInt())
    }

    fun nextQuestion() {
        val selected = _selectedAnswer.value ?: return
        _answers.update { it + AnswerHistory(_quizQuestions.value[it.size], selected) }
        _selectedAnswer.value = null
    }

    fun restartQuiz() {
        _countInput.value = ""
        _quizQuestions.value = emptyList()
        _answers.value = emptyList()
        _selectedAnswer.value = null
    }
}