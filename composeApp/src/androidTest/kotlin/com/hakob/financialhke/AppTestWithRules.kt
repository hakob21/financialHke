package com.hakob.financialhke

import androidx.activity.ComponentActivity
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyPress
import androidx.compose.ui.test.performTextInput
import cafe.adriel.voyager.navigator.Navigator
import com.hakob.financialhke.codeUtils.ClockProvider
import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.db.repositoryimpl.ExpenseRepository
import com.hakob.financialhke.koin.coreModule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

//class AppTestWithRules : KoinTest {
class AppTestWithRules {

    @get:Rule
//    val rule = createComposeRule()
    val rule = createAndroidComposeRule<ComponentActivity>()

    val budgetInputTextField = rule.onNodeWithTag("budgetInputTextField")
    val dayInCalendar = rule.onNodeWithTag("day-2024-05-31")
    val submitButton = rule.onNodeWithTag("submitButton")

    // second screen
    val textWithWholeBudget = rule.onNodeWithTag("secondScreenWholeBudget")
    val textWithDailyAvailableAmount = rule.onNodeWithTag("secondScreenDailyAvailableAmount")
    val expenseInputTextField = rule.onNodeWithTag("expenseInputTextField")


    val startScreen = SecondScreen()

//    private val expenseRepository: ExpenseRepositoryInterface by inject(ExpenseRepository::class.java)


//    @Before
//    fun tearDown() {
//        stopKoin()
//        startKoin {
//            modules(
//                coreModule()
////                    .plus(
////                    // will override the ActulaClockProvider dependency
////                    module {
//////                        single<ClockProvider> {
//////                            AlwaysJuneFirstClockProvider()
//////                        }
////                    }
////                )
////                module {
////                    single<ExpenseRepositoryInterface> { ExpenseRepository(get()) }
////
////                    single { Realm.open(get()) }
////
////                    single<Configuration> {
////                        RealmConfiguration.Builder(
////                            schema = setOf(
////                                com.hakob.financialhke.db.repodomain.Expense::class
////                            )
////                        ).schemaVersion(1).build()
////                    }
////
////                    single<BusinessLogic> { BusinessLogic(get()) }
////                }
//            )
//        }
//        expenseRepository.deleteAllExpenses()
//        expenseRepository.deleteAllBudgets()
//    }

    @Test
    fun test1() {
        // should set budget and some end day and submit and check that on the second screen daily available budget and whole budget for the month are correct
        rule.setContent {
            Navigator(startScreen)
        }
        val textInput = "1000"

        Thread.sleep(1000)
        budgetInputTextField.performTextInput(textInput)
        budgetInputTextField.assert(hasText(textInput))
        Thread.sleep(1000)
        dayInCalendar.performClick()
        submitButton.performClick()
        textWithWholeBudget.assert(hasText("Whole budget for the month 1000"))
        textWithDailyAvailableAmount.assert(hasText("Daily available budget 333"))

        Thread.sleep(10000)
    }

    @Test
    fun test2() {
        // should set budget and some end day and submit and check that on the second screen daily available budget and whole budget for the month are correct and then go back a screen and set all again with different value and check values on the second screen
        rule.setContent {
            Navigator(startScreen)
        }
        val textInput = "1000"

        Thread.sleep(1000)
        budgetInputTextField.performTextInput(textInput)
        budgetInputTextField.assert(hasText(textInput))
        Thread.sleep(1000)
        dayInCalendar.performClick()
        submitButton.performClick()
        textWithWholeBudget.assert(hasText("Whole budget for the month 1000"))
        textWithDailyAvailableAmount.assert(hasText("Daily available budget 333"))
        Thread.sleep(1000)

        rule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
//        rule.onRoot().performKeyPress(KeyEvent())
        Thread.sleep(1000)

        val secondTextInput = "500"
        val secondTimeDayInCalendar = rule.onNodeWithTag("day-2024-05-30")

        budgetInputTextField.performTextInput(secondTextInput)
        budgetInputTextField.assert(hasText(secondTextInput))
        secondTimeDayInCalendar.performClick()
        submitButton.performClick()
//        Thread.sleep(10000)
        textWithWholeBudget.assertHasNoClickAction()
        textWithWholeBudget.assert(hasText("Whole budget for the month 500"))
        textWithDailyAvailableAmount.assert(hasText("Daily available budget 250"))

        Thread.sleep(10000)
    }


    @Test
    fun test3() {
        // should set budget and some end day and submit and check that on the second screen daily available budget and whole budget for the month are correct
        rule.setContent {
            Navigator(startScreen)
        }
        val textInput = "1000"

        Thread.sleep(1000)
        budgetInputTextField.performTextInput(textInput)
        budgetInputTextField.assert(hasText(textInput))
        Thread.sleep(1000)
        dayInCalendar.performClick()
        submitButton.performClick()
        textWithWholeBudget.assert(hasText("Whole budget for the month 1000"))
        textWithDailyAvailableAmount.assert(hasText("Daily available budget 333"))

        expenseInputTextField.performTextInput("500")
        val buttonSubmitExpense = rule.onNodeWithTag("addExpenseButton")
        buttonSubmitExpense.performClick()
        textWithWholeBudget.assert(hasText("Whole budget for the month 500"))
        textWithDailyAvailableAmount.assert(hasText("Daily available budget 166"))
        Thread.sleep(10000)
    }
}