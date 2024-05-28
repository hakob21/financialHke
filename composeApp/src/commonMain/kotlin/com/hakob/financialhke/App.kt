package com.hakob.financialhke

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import io.realm.kotlin.types.RealmInstant
import io.wojciechosak.calendar.config.MonthYear
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.config.toLocalDate
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import kotlin.random.Random

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinContext {
        AppContent()
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
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
                label = { Text("Budget") },
                keyboardActions = KeyboardActions(
//                    onAny = {
//                        println("HKEEE DONE TAPPED")
//
//                    },
                    onDone = {
                        val result: Result<Unit> = runCatching {
                            businessLogic.setBudget(
                                Budget(
                                    sum = textBudget.toDouble(),
                                    endLocalDateTime = LocalDateTime(
                                        LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59)
                                    )
                                )
                            )
                        }
//                        println("All expenses: ${businessLogic.getAllExpenses()}")

                    }
                )
            )
//            TextField(
//                value = textExpense,
//                onValueChange = { textExpense = it },
//                singleLine = true,
////                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//                label = { Text("ExpensePlaceholder") },
//                keyboardActions = KeyboardActions(
////                    onAny = {
////                        println("HKEEE DONE TAPPED")
////
////                    },
//                    onDone = {
//                        val result: Result<Unit> = runCatching {
//                            businessLogic.enterExpense(Expense(textExpense.toDouble()))
//                        }
//                        println("HKEEE DONE TAPPED")
//                        println("All expenses: ${businessLogic.getAllExpenses()}")
//
//                    }
//                )
//            )
            CalendarView(
                day = { state ->
                    DayView(
                        date = state.date,
                        isDotVisible = state.isActiveDay || Random.nextBoolean(),
                        onClick = { },
                    )
                },
                config =
                rememberCalendarState(
                    startDate = MonthYear(year = 1994, month = Month.APRIL).toLocalDate(),
                    monthOffset = 0,
                    showNextMonthDays = false,
                    showPreviousMonthDays = false,
                    showHeader = false,
                    showWeekdays = false,
                ),
            )

            HorizontalCalendarView(startDate = LocalDate(2024, 5, 21)) { monthOffset ->
                CalendarView(
                    config = rememberCalendarState(
                        startDate = LocalDate(2024, 5, 21),
                        monthOffset = 0
                    ),
                    day = { dayState ->
                        // define your day composable here!
                    }
                )
            }

            Button(
                onClick = {
                    runCatching {
                        businessLogic.setBudget(
                            Budget(
                                sum = textBudget.toDouble(),
                                endLocalDateTime = LocalDateTime(
                                    LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59)
                                )
                            )
                        )
                    }
                },
                content = {
                    Text("Submit")

                },
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


@Composable
private fun DayView(
    date: LocalDate,
    onClick: () -> Unit = {},
    isDotVisible: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Box {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.aspectRatio(1f).padding(3.dp),
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(0.dp, Color.Transparent),
            colors =
            ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color(0xffdaa92a),
            ),
        ) {
            Text(
                "${date.dayOfMonth}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }
        if (isDotVisible) {
            Canvas(
                modifier =
                Modifier
                    .padding(bottom = 10.dp)
                    .size(8.dp)
                    .align(Alignment.BottomCenter),
                onDraw = { drawCircle(color = Color(0xff2d2cb2)) },
            )
        }
    }
}
