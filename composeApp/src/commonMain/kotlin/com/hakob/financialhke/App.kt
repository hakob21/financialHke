package com.hakob.financialhke

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.hakob.financialhke.domain.Expense
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import financialhke.composeapp.generated.resources.Res
import financialhke.composeapp.generated.resources.compose_multiplatform
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
    greeting: Greeting = koinInject()
) {
    var text by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            TextField(
                value = text,
                onValueChange = { text = it },
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
                            greeting.enterExpense(Expense(text.toDouble()))
                        }
                        println("HKEEE DONE TAPPED")
                        println("All expenses: ${greeting.getAllExpenses()}")

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