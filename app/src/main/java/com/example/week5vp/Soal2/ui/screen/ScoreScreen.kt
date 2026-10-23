package com.example.week5vp.Soal2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week5vp.Soal2.model.AnswerHistory

@Composable
fun ScoreScreen(
    score: Int,
    total: Int,
    answers: List<AnswerHistory>,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val wrongAnswers = answers.withIndex().filter { !it.value.isCorrect }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("Quiz Finished!", fontSize = 20.sp)
            Spacer(Modifier.height(8.dp))
            Text("Your Score: $score / $total", fontSize = 14.sp)
            Spacer(Modifier.height(16.dp))
        }

        if (wrongAnswers.isEmpty()) {
            item {
                Text(
                    "Perfect! No wrong answers.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            items(wrongAnswers) { (index, record) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text("Q${index + 1}: ${record.question.text}", fontSize = 12.sp)
                    Text(
                        "Your Answer: ${record.selectedAnswer}",
                        fontSize = 10.sp,
                        color = Color.Red
                    )
                    Text(
                        "Correct Answer: ${record.question.correctAnswer}",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(6.dp))
                    HorizontalDivider()
                }
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onRestart,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Restart Quiz", fontSize = 12.sp)
            }
        }
    }
}