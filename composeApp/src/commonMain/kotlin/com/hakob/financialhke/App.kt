package com.hakob.financialhke

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import io.realm.kotlin.types.RealmInstant
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinContext {
        AppContent()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun AppContent(
    businessLogic: BusinessLogic = koinInject()
) {
    var textBudget by remember { mutableStateOf("") }
    var textExpense by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            TextField(
                value = textBudget,
                onValueChange = { textBudget = it },
                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                label = { Text("Expense") },
                keyboardActions = KeyboardActions(
//                    onAny = {
//                        println("HKEEE DONE TAPPED")
//
//                    },
                    onDone = {
                        val result: Result<Unit> = runCatching {
//                            businessLogic.setBudget(Budget(sum = textBudget.toDouble(), localDate = ))
                        }
                        println("All expenses: ${businessLogic.getAllExpenses()}")

                    }
                )
            )
            TextField(
                value = textExpense,
                onValueChange = { textExpense = it },
                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                label = { Text("Expense") },
                keyboardActions = KeyboardActions(
//                    onAny = {
//                        println("HKEEE DONE TAPPED")
//
//                    },
                    onDone = {
                        val result: Result<Unit> = runCatching {
                            businessLogic.enterExpense(Expense(textExpense.toDouble()))
                        }
                        println("HKEEE DONE TAPPED")
                        println("All expenses: ${businessLogic.getAllExpenses()}")

                    }
                )
            )
            Button(
                content = { Unit },
                onClick = { Unit }
            )
//            Text(text = "hello1")
//            Text(text = "hello2")
//            greeting.greet()
        }
//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { greeting.greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
    }
}