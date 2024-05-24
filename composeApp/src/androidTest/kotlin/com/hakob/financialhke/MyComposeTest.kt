package com.hakob.financialhke

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

// file: app/src/androidTest/java/com/package/MyComposeTest.kt

class MyComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            App()
        }

        composeTestRule.onNodeWithText("HkeExpense").performClick()
        println("fsfa")

//        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
}