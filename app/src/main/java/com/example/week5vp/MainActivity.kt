package com.example.week5vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.week5vp.Soal1.ui.screen.ExpenseScreen
import com.example.week5vp.Soal2.ui.screen.StateScreen
import com.example.week5vp.ui.theme.Week5VPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week5VPTheme {
                StateScreen()
            }
        }
    }
}