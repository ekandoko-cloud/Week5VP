package com.example.week5vp.Soal1.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5vp.Soal1.model.Expense
import com.example.week5vp.Soal1.viewmodel.ExpenseViewModel

private fun formatRupiah(price: Int): String {
    return "Rp " + String.format("%,d", price).replace(',', '.')
}

@Composable
fun SpendingScreen(viewModel: ExpenseViewModel = viewModel()) {
    val spending by viewModel.spending.collectAsState()
    val inputName by viewModel.inputName.collectAsState()
    val inputPrice by viewModel.inputPrice.collectAsState()
    val errorName by viewModel.errorName.collectAsState()
    val errorPrice by viewModel.errorPrice.collectAsState()
    var editing by remember { mutableStateOf<Expense?>(null) }
    var editText by remember { mutableStateOf("") }
    var editError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 14.dp, end = 14.dp)
    ) {
        Text("Expenses", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Total Expenses : ${formatRupiah(viewModel.grandTotal())}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputName,
            onValueChange = viewModel::onNameChange,
            label = { Text("Expense Name") },
            singleLine = true,
            isError = errorName != null,
            supportingText = {
                if (errorName != null) Text(errorName!!, fontSize = 10.sp)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = inputPrice,
                onValueChange = viewModel::onPriceChange,
                label = { Text("Amount of Expense") },
                singleLine = true,
                isError = errorPrice != null,
                supportingText = {
                    if (errorPrice != null) Text(errorPrice!!, fontSize = 10.sp)
                },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { viewModel.addSpending() }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Expense")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        val edit = editing
        if (edit != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = cardColors(Color.White)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Edit ${edit.name}", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = editText,
                        onValueChange = {
                            editText = it.filter { c -> c.isDigit() }
                            editError = false
                        },
                        label = { Text("Amount of Expense") },
                        singleLine = true,
                        isError = editError,
                        supportingText = {
                            if (editError) Text("Amount must be greater than 0", fontSize = 10.sp)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = { editing = null }) {
                            Text("Cancel", color = Color.Red)
                        }
                        TextButton(onClick = {
                            if (viewModel.changePrice(edit.id, editText)) {
                                editing = null
                            } else {
                                editError = true
                            }
                        }) {
                            Text("Save")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(spending, key = { it.id }) { item ->
                Card(
                    colors = cardColors(Color(0xFFDCE2F9)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(start = 14.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 20.dp)
                        ) {
                            Text(
                                "Name : ${item.name}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(formatRupiah(item.price), fontSize = 14.sp)
                        }
                        IconButton(onClick = {
                            editing = item
                            editText = item.price.toString()
                            editError = false
                        }) {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFFC99700)
                            )
                        }
                        IconButton(onClick = {
                            viewModel.removeSpending(item.id)
                            if (editing?.id == item.id) editing = null
                        }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Remove",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SpendingScreenPreview() {
    SpendingScreen(viewModel = ExpenseViewModel())
}