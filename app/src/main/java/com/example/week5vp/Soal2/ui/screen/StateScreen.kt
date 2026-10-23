package com.example.week5vp.Soal2.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5vp.Soal2.viewmodel.QuizViewModel

@Composable
fun StateScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    val total = viewModel.listQuiz.size
    val done = viewModel.answers.size

    if (total == 0) {
        StartScreen(
            maxQuestions = viewModel.maxQuestions,
            countInput = viewModel.countInput,
            errorMessage = viewModel.countError,
            canStart = viewModel.canStart,
            onCountChange = { viewModel.countInput = it },
            onStart = viewModel::startQuiz,
            modifier = modifier
        )
    } else if (done == total) {
        ScoreScreen(
            score = viewModel.score,
            total = total,
            answers = viewModel.answers,
            onRestart = viewModel::restartQuiz,
            modifier = modifier
        )
    } else {
        QuizScreen(
            questionNumber = done + 1,
            question = viewModel.listQuiz[done],
            selectedAnswer = viewModel.selectedAnswer,
            onSelect = { viewModel.selectedAnswer = it },
            onNext = viewModel::nextQuestion,
            modifier = modifier
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun StateScreenPreview() {
    StateScreen(modifier = Modifier)
}
