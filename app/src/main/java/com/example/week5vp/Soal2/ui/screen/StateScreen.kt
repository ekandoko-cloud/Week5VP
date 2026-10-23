package com.example.week5vp.Soal2.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicerabu.q2.viewmodel.QuizViewModel

@Composable
fun StateScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    val countInput by viewModel.countInput.collectAsState()
    val questions by viewModel.quizQuestions.collectAsState()
    val answers by viewModel.answers.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()

    if (questions.isEmpty()) {
        StartScreen(
            maxQuestions = viewModel.maxQuestions,
            countInput = countInput,
            errorMessage = viewModel.countError(),
            canStart = viewModel.canStart(),
            onCountChange = viewModel::onCountChange,
            onStart = viewModel::startQuiz,
            modifier = modifier
        )
    } else if (answers.size == questions.size) {
        ScoreScreen(
            score = viewModel.score(),
            total = questions.size,
            answers = answers,
            onRestart = viewModel::restartQuiz,
            modifier = modifier
        )
    } else {
        QuizScreen(
            questionNumber = answers.size + 1,
            question = questions[answers.size],
            selectedAnswer = selectedAnswer,
            onSelect = viewModel::selectAnswer,
            onNext = viewModel::nextQuestion,
            modifier = modifier
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun StateScreenPreview() {
    StateScreen()
}