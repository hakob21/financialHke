package com.hakob.financialhke

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import cafe.adriel.voyager.navigator.Navigator
import org.junit.Rule
import org.junit.Test

//class AppTestWithRules : KoinTest {
class AppTestWithRules {

    @get:Rule
    val rule = createComposeRule()

    val budgetInputTextField = rule.onNodeWithTag("budgetInputTextField")
    val dayInCalendar = rule.onNodeWithTag("day-2024-05-31")
    val submitButton = rule.onNodeWithTag("submitButton")

    // second screen
    val textWithWholeBudget = rule.onNodeWithTag("secondScreenWholeBudget")
    val textWithDailyAvailableAmount = rule.onNodeWithTag("secondScreenDailyAvailableAmount")

    @Test
    fun test() {
        rule.setContent {
            Navigator(HomeScreen())
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
}