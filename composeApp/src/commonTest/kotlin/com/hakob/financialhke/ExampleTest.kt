package com.hakob.financialhke

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import kotlin.test.Test

class ExampleTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun myTest() = runComposeUiTest {
        setContent {
            var text by remember { mutableStateOf("Hello") }
            Text(
                text = text,
                modifier = Modifier.testTag("text")
            )
            Button(
                onClick = { text = "Compose" },
                modifier = Modifier.testTag("button")
            ){
                Text("Click me")
            }
        }

        onNodeWithTag("text").assertTextEquals("Hello")
        onNodeWithTag("button").performClick()
        onNodeWithTag("text").assertTextEquals("Compose") // works with IOS but not with Android
    }

    // HKEEEEEEE
    // https://docs.google.com/document/d/1e7IlnBkJ5w6vJV5LqnXFPCGvomTdDxdXZRbfyBXQYsM/edit?pli=1 got all the instructions of current uncommited changes from here, but with command in CLI ./gradlew :shared:connectedAndroidTest it says BUILD SUCCESSFUL even if I deliverately try to make test fail with assertions
}
