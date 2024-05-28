package com.hakob.financialhke

import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import com.hakob.financialhke.koin.coreModule
import io.mockative.every
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BusinessLogicIntegrationTest: KoinTest {

    private val expenseRepository: ExpenseRepositoryInterface by inject()

    private val businessLogic: BusinessLogic by inject()

    @BeforeTest
    fun tearDown() {
        startKoin {
            modules(
                coreModule()
//                module {
//                    single<ExpenseRepositoryInterface> { ExpenseRepository(get()) }
//
//                    single { Realm.open(get()) }
//
//                    single<Configuration> {
//                        RealmConfiguration.Builder(
//                            schema = setOf(
//                                com.hakob.financialhke.db.repodomain.Expense::class
//                            )
//                        ).schemaVersion(1).build()
//                    }
//
//                    single<BusinessLogic> { BusinessLogic(get()) }
//                }
            )
        }
        expenseRepository.deleteAllExpenses()
        expenseRepository.deleteAllBudgets()
    }

    // business logic layer test
    @Test
    fun `should add expense to the db and subtract from the whole budget`() {
//        val instantNow: Instant = Clock.System.now()
//        instantNow.toString()  // returns something like 2015-12-31T12:30:00Z
        // hke how can we parse and convert
//        val endLocalDate: LocalDate = LocalDate.parse("2024-05-26")
//        println(endLocalDate)


        // given
        val now: Instant = Clock.System.now()
        val today: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
// or shorter
//        val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val budget = Budget(
            sum = 1000.0,
            localDate = today
        )
//        every { expenseRepository.setBudget(budget) }.returns(budget)

        // when
        val actualBudget = businessLogic.setBudget(budget)

        // then
        assertEquals(expected = budget, actual = actualBudget)
    }

    @Test
    fun `should set budget and get for current month`() {
        // given
        // setting budget until the end of june
        val endOfJune = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))

        val budget = Budget(
            sum = 1000.0,
            localDate = endOfJune
        )

        // when
        val insertedBudget = businessLogic.setBudget(budget)
        val fetchedBudget = businessLogic.getCurrentBudget()

        // then
        assertEquals(expected = budget, actual = fetchedBudget)
    }

    // business logic layer test
    @Test
    fun `new updated test should set the budget`() {
        // given
        val endOfJune = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))
// or shorter
//        val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val budget = Budget(
            sum = 1000.0,
            localDate = endOfJune
        )

        // when
        val actualBudget = businessLogic.setBudget(budget)

        // then
        assertEquals(expected = budget, actual = actualBudget)
    }


    // business logic layer test 2
    @Test
    fun `should set budget then add expense to the db with subtracting from the current month's budget`() {
        // given
        // set budget until end of june
        val endOfJune = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))
        val budget = Budget(
            sum = 1000.0,
            localDate = endOfJune
        )
        val actualBudget = businessLogic.setBudget(budget)

        // add expense
        val expense = Expense(
            sum = 200.0,
        )

        // when
        val currentBudgetBeforeAddingExpense = businessLogic.getCurrentBudget()
        val actualAddedExpense = businessLogic.enterExpense(expense)
        val currentBudgetAfterAddingExpense = businessLogic.getCurrentBudget()

        // then
        println("hke debug currentBudgetBeforeAddingExpense ${currentBudgetBeforeAddingExpense}")
        println("hke debug currentBudgetAfterAddingExpense ${currentBudgetAfterAddingExpense}")
        assertEquals(expected = expense, actual = actualAddedExpense)
        assertEquals(expected = budget.sum - expense.sum, actual = currentBudgetAfterAddingExpense.sum)
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