package com.example.week5vp.Soal2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartScreen(
    maxQuestions: Int,
    countInput: String,
    errorMessage: String?,
    canStart: Boolean,
    onCountChange: (String) -> Unit,
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mini Quiz", fontSize = 20.sp)
        Text("How many questions do you want? (1 - $maxQuestions)", fontSize = 12.sp)

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = countInput,
            onValueChange = onCountChange,
            label = { Text("Number of questions") },
            isError = errorMessage != null,
            supportingText = {
                if (errorMessage != null) Text(errorMessage)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onStart,
            enabled = canStart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Quiz", fontSize = 12.sp)
        }
    }
}