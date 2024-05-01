package com.hakob.financialhke.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// as @Preview works only in android classes, can create temporarily such functions, work on the looking at the preview and
// then copy to the common module
@Composable
@Preview
fun ExpenseComposablePreview() {
    Row (
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "hello")
        Text(text = "hi")
        Text(text = "hakob")
        Text(text = "yo")
    }
}