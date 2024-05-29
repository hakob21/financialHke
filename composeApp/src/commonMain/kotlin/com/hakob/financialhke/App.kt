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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakob.financialhke.codeUtils.ClockProvider
import com.hakob.financialhke.codeUtils.toLocalDateTimeTowardsEod
import com.hakob.financialhke.composables.ExpenseComposable
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import io.wojciechosak.calendar.config.MonthYear
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.config.toLocalDate
import io.wojciechosak.calendar.view.CalendarView
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
    businessLogic: BusinessLogic = koinInject(),
    clockProvider: ClockProvider = koinInject(),
) {
    var textBudget by remember { mutableStateOf("") }
    var textExpense by remember { mutableStateOf("") }
    var pickedLocalDate by remember { mutableStateOf("") }

    val navigator = LocalNavigator.currentOrThrow

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
                ),
                modifier = Modifier.testTag("budgetInputTextField")
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
                        onClick = {
                            val localDateOfPickedDate: LocalDate = state.date
                            pickedLocalDate = localDateOfPickedDate.toString()
                            println("hkeee instantOfPickedDate $localDateOfPickedDate")
//                            val instantOfPickedDate = state.date.toLocalDateTimeTowardsEod().toInstant(TimeZone.UTC)
//                            insta = instantOfPickedDate.toString()
//                            println("hkeee instantOfPickedDate $instantOfPickedDate")

                        },
                        modifier = Modifier.testTag("day-${state.date}")
                    )
                },
                config = rememberCalendarState(
                    startDate = MonthYear(
                        year = clockProvider.getCurrentLocalDateTime().year,
                        month = clockProvider.getCurrentLocalDateTime().month
                    ).toLocalDate(),
                    monthOffset = 0,
                    showNextMonthDays = false,
                    showPreviousMonthDays = false,
                    showHeader = false,
                    showWeekdays = false,
                ),
            )

//            HorizontalCalendarView(
//                startDate = LocalDate(2024, 5, 21)
//            ) { monthOffset ->
//                CalendarView(
//                    config = rememberCalendarState(
//                        startDate = LocalDate(2024, 5, 21),
//                        monthOffset = 0
//                    ),
//                    day = { dayState ->
//                        // define your day composable here!
//                    }
//                )
//            }

            Button(
                onClick = {
                    runCatching {
                        businessLogic.setBudget(
                            Budget(
                                sum = textBudget.toDouble(),
                                endLocalDateTime = LocalDate.parse(pickedLocalDate)
                                    .toLocalDateTimeTowardsEod()
//                                LocalDateTime(
//                                    LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59)
//                                )
                            )
                        )
                    }

                    navigator.push(SecondScreen())
                },
                content = {
                    Text("Submit")
                },
                modifier = Modifier.testTag("submitButton")
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
            colors = ButtonDefaults.outlinedButtonColors(
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
        // Dot color and other parameters
        if (isDotVisible) {
            Canvas(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .size(8.dp)
                    .align(Alignment.BottomCenter),
                onDraw = { drawCircle(color = Color.Red) },
            )
        }
    }
}

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        App()
    }
}

class SecondScreen : Screen {

    @Composable
    override fun Content() {
        var inputTextExpense by remember { mutableStateOf("") }
        var textExpense by remember { mutableStateOf("") }
        var pickedLocalDate by remember { mutableStateOf("") }
        val businessLogic: BusinessLogic = koinInject()
//        val expensesList: MutableState<List<Expense>> = remember(businessLogic.getAllExpenses()) {
        val expensesList: SnapshotStateList<Expense> = remember {
            mutableStateListOf(*businessLogic.getAllExpenses().reversed().toTypedArray())
        }

        val dailyAvailableBudget = remember {
            mutableStateOf(businessLogic.getDailyAvailableAmount())
        }

        val currentMonthBudget = remember {
            mutableStateOf(businessLogic.getCurrentBudget())
        }

        val navigator = LocalNavigator.currentOrThrow

        MaterialTheme {
            LazyColumn {
                item {
                    Text(
                        text = "Whole budget for the month ${currentMonthBudget.value.sum.toInt()}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.testTag("secondScreenWholeBudget")
                    )
                }
                item {
                    Text(
                        text = "Daily available budget ${dailyAvailableBudget.value}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.testTag("secondScreenDailyAvailableAmount")
                    )
                }
                item {
                    TextField(
                        value = inputTextExpense,
                        onValueChange = { inputTextExpense = it },
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
//                                    businessLogic.setBudget(
//                                        Budget(
//                                            sum = inputTextExpense.toDouble(),
//                                            endLocalDateTime = LocalDateTime(
//                                                LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59)
//                                            )
//                                        )
//                                    )
                                }
//                        println("All expenses: ${businessLogic.getAllExpenses()}")

                            }
                        ),
                        modifier = Modifier.testTag("expenseInputTextField")
                    )
                }
                item {
                    Button(
                        onClick = {
                            runCatching {
                                businessLogic.enterExpense(
                                    expense = Expense(
                                        sum = inputTextExpense.toDouble()
                                    )
                                )
                            }
                            expensesList.add(
                                0,
                                Expense(
                                    sum = inputTextExpense.toDouble()
                                )
                            )

                            dailyAvailableBudget.value = businessLogic.getDailyAvailableAmount()
                            currentMonthBudget.value = businessLogic.getCurrentBudget()


                        },
                        content = {
                            Text("Add expense")
                        },
                        modifier = Modifier.testTag("addExpenseButton")
                    )
                }
                items(
//                count = expenseRepository.expenses().size,
                    items = expensesList
                ) { expense ->
                    ExpenseComposable(expense)
                }
            }
//            LazyColumn() {
//            }

//            Button(
//                onClick = { Unit }
//            ) {
//                Text(budgetSum.toString())
//
//            }
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
}

//class SingleActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            Navigator(HomeScreen())
//        }
//    }
//}
