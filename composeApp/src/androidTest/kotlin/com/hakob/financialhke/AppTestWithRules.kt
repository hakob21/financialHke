package com.hakob.financialhke

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.hakob.financialhke.codeUtils.ClockProvider
import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import com.hakob.financialhke.koin.coreModule
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

//class AppTestWithRules : KoinTest {
class AppTestWithRules {
    val expenseField = hasText("")

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun test() {
        rule.setContent { App() }

        Thread.sleep(2000)
        rule.onNodeWithText("Expense")
        Thread.sleep(2000)
    }
}