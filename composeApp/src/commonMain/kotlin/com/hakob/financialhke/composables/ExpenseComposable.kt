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
import androidx.compose.ui.unit.dp
import com.hakob.financialhke.domain.Expense
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

//https://www.youtube.com/watch?v=c6TJQixS51Y&list=PLSrm9z4zp4mEWwyiuYgVMWcDFdsebhM-r&index=16
@Composable
fun ExpenseComposable(
    expense: Expense
) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (expense.localDate.compareTo(
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                ) == 0
            ) {
                "Today"
            } else expense.localDate.toString()
        )
        Text(text = expense.sum.toString())
//        Text(text = "hi")
//        Text(text = "hakob")
//        Text(text = "yo")
    }
}