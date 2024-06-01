@file:OptIn(ExperimentalTestApi::class)
package com.hakob.financialhke.UiTests

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

@Composable
fun Screen() {
    var count by remember { mutableStateOf(0) }

    Column {
        Text(text = "Count")
        Text(text = count.toString())
        Button(onClick = { count++ }) {
            Text("Button")
        }
    }
}

class UiTests {

    @Test
    fun initial_screen_state() = runComposeUiTest {
        setContent {
            Screen()
        }

        onNodeWithText("Count").assertIsDisplayed()
        onNodeWithText("0").assertIsDisplayed()
        onNodeWithText("Button").assertIsDisplayed()
    }

//    @Test
//    fun `increment counter`() = runComposeUiTest {
//        setContent {
//            Screen()
//        }
//
//        onNodeWithText("0").assertIsDisplayed()
//        onNodeWithText("Button").performClick()
//        onNodeWithText("1").assertIsDisplayed()
//    }
}
