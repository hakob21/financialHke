package com.hakob.financialhke

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.hakob.financialhke.codeUtils.ClockProvider
import com.hakob.financialhke.composables.ExpenseComposable
import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.math.roundToInt
import kotlin.time.Duration

class BusinessLogic(
    val expenseRepository: ExpenseRepositoryInterface,
    val clockProvider: ClockProvider,
) {
    private val platform = getPlatform()

    @Composable
    fun greet(): String {
//        doDbThings()
        LazyColumn() {
            items(
//                count = expenseRepository.expenses().size,
                items = expenseRepository.expenses()
            ) {
                expense -> ExpenseComposable(expense)
            }
        }
        return "Hello, ${platform.name}!"
    }

    fun doDbThings() {
        // this gets stored (at least in case of IOS) permanently. on the first run the row is
        // inserted in the DB, but next runs it fails
        // because the primary key id is already present in the DB.
        // can change the ID and run again every time
        val entry = Expense(3.3)
        expenseRepository.addExpense(entry)
        check(expenseRepository.expenses().contains(entry))
        println("Hkeeeee expenses ${expenseRepository.expenses()}")
        // uncomment these two lines to clear database
//        expenseRepository.deleteAll()
//        check(expenseRepository.expenses().isEmpty())
    }

    fun getDailyAvailableAmount(): Int {
        val currentLocalDateTime: LocalDateTime = clockProvider.getCurrentLocalDateTime()
        val currentBudgetForMonth = getCurrentBudget()

        val instantOne = currentLocalDateTime.toInstant(TimeZone.UTC)
        val instantTwo = currentBudgetForMonth.endLocalDateTime.toInstant(TimeZone.UTC)
        val daysLeftUntilEndOfCycle: Duration = instantTwo.minus(instantOne)
        // +1 so that it includes the last day. e.g. from 1st until 3rd of June it should be counter as 3 days of budget
        val wholeDaysLeftUntilEndOfCycle = daysLeftUntilEndOfCycle.inWholeDays + 1
        println("hkee wholedays : ${wholeDaysLeftUntilEndOfCycle}")

        val dailyAvailableAmount = currentBudgetForMonth.sum / wholeDaysLeftUntilEndOfCycle
        val dailyAvailableAmountRound = dailyAvailableAmount.roundToInt()
        return dailyAvailableAmountRound
    }

    fun enterExpense(expense: Expense): Expense {
        return expenseRepository.addExpense(expense)
    }
    fun getAllExpenses(): List<Expense> {
        return expenseRepository.expenses()
    }

    fun getCurrentBudget(): Budget {
//        val currentLocalDateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val currentLocalDateTime: LocalDateTime = clockProvider.getCurrentLocalDateTime()

        return expenseRepository.getCurrentBudget(currentLocalDateTime)
    }

    fun setBudget(budget: Budget): Budget {
        expenseRepository.deleteAllBudgets()
        expenseRepository.deleteAllExpenses()
        // extract Clock or Clock.System to a variable of this class, so that it can be injected from the test and in test always return the same value
        // at work codebase we have such providers
        val currentLocalDateTime = clockProvider.getCurrentLocalDateTime()
        println("Hkee currentLocalDateTime ${currentLocalDateTime}")
        if(budget.endLocalDateTime.date.compareTo(currentLocalDateTime.date) >= 0) {
            return expenseRepository.setBudget(budget)
        } else {
            throw RuntimeException("The time set in the budget is earlier than current localDateTime ")
        }
    }
}