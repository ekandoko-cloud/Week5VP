package com.example.week5vp.Soal1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week5vp.Soal1.model.Expense

class ExpenseViewModel : ViewModel() {

    // State
    var spending by mutableStateOf<List<Expense>>(emptyList())
    var titleInput by mutableStateOf("")
    var priceInput by mutableStateOf("")
    var titleError by mutableStateOf<String?>(null)
    var priceError by mutableStateOf<String?>(null)

    private var idCounter = 1

    // Otomatis ikut berubah kalau daftar spendings berubah (tambah / hapus / edit)
    val grandTotal: Int
        get() = spending.sumOf { it.price }

    fun addSpending() {
        val parsedPrice = priceInput.toIntOrNull()

        if (titleInput.isBlank()) {
            titleError = "Name must not be empty"
            return
        }
        if (parsedPrice == null || parsedPrice <= 0) {
            priceError = "Amount must be greater than 0"
            return
        }

        spending = spending + Expense(idCounter, titleInput.trim(), parsedPrice)
        idCounter += 1

        // Kosongkan input dan error
        titleInput = ""
        priceInput = ""
        titleError = null
        priceError = null
    }

    fun removeSpending(id: Int) {
        spending = spending.filter { it.id != id }
    }

    // Return true kalau berhasil, false kalau harga tidak valid
    fun changePrice(id: Int, newValue: String): Boolean {
        val parsedPrice = newValue.toIntOrNull()
        if (parsedPrice == null || parsedPrice <= 0) {
            return false
        }
        spending = spending.map {
            if (it.id == id) it.copy(price = parsedPrice) else it
        }
        return true
    }
}