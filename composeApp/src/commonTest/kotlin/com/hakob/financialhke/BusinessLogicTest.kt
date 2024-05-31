package com.hakob.financialhke

import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import com.hakob.financialhke.utils.annotation.testCodeUtils.AlwaysJuneFirstClockProvider
import com.hakob.financialhke.utils.annotation.testCodeUtils.AlwaysJune30ClockProvider
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.every
import io.mockative.mock
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

// test for common code presumably needs to be stored here. but AS created it in the androidTest directory


//import org.junit.jupiter.api.Assertions.*
//
//class GreetingTest {
//
//
//            @AfterTest
//    fun tearDown() {
//    }
//
//    @Test
//    fun greet() {
//        println("hkeeee")
//    }
//}
class BusinessLogicTest {

    @Mock
    private val expenseRepository: ExpenseRepositoryInterface = mock(classOf<ExpenseRepositoryInterface>())

    private val alwaysJuneFirstClockProvider: AlwaysJuneFirstClockProvider = AlwaysJuneFirstClockProvider()

    private val alwaysJune30ClockProvider: AlwaysJune30ClockProvider = AlwaysJune30ClockProvider()

    private val businessLogic: BusinessLogic = BusinessLogic(expenseRepository, alwaysJuneFirstClockProvider)

    @AfterTest
    fun tearDown() {
    }

    // business logic layer test
    @Test
    fun `should set the budget`() {
//        val instantNow: Instant = Clock.System.now()
//        instantNow.toString()  // returns something like 2015-12-31T12:30:00Z
        // hke how can we parse and convert
//        val endLocalDate: LocalDate = LocalDate.parse("2024-05-26")
//        println(endLocalDate)


        // given
        val now: Instant = Clock.System.now()
        val endLocalDateTime: LocalDateTime = alwaysJune30ClockProvider.getCurrentLocalDateTime()
// or shorter
//        val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val budget = Budget(
            sum = 1000.0,
            endLocalDateTime = endLocalDateTime
        )
        every { expenseRepository.setBudget(budget) }.returns(budget)

        // when
        val actualBudget = businessLogic.setBudget(budget)

        // then
        assertEquals(expected = budget, actual = actualBudget)
    }

    // repo layer test
    @Test
    fun greet() {
        val expected = listOf(Expense(sum = 3.0))
        every { expenseRepository.expenses() }.returns(listOf(Expense(sum = 3.0)))

        val actual = expenseRepository.expenses()

        assertEquals(expected, actual)
//        Assertion().assertEquals(greeting.greet(), "Hello, Android!")
    }
}