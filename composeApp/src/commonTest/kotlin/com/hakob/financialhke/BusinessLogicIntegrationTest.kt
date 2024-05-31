package com.hakob.financialhke

import com.hakob.financialhke.codeUtils.ClockProvider
import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import com.hakob.financialhke.koin.coreModule
import com.hakob.financialhke.utils.annotation.testCodeUtils.AlwaysJuneFirstClockProvider
import io.mockative.every
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BusinessLogicIntegrationTest : KoinTest {

    private val expenseRepository: ExpenseRepositoryInterface by inject()

    private val businessLogic: BusinessLogic by inject()

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                coreModule().plus(
                    // will override the ActulaClockProvider dependency
                    module {
                        single<ClockProvider> {
                            AlwaysJuneFirstClockProvider()
                        }
                    }
                )
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
    fun should_add_expense_to_the_db_and_subtract_from_the_whole_budget() {
//        val instantNow: Instant = Clock.System.now()
//        instantNow.toString()  // returns something like 2015-12-31T12:30:00Z
        // hke how can we parse and convert
//        val endLocalDate: LocalDate = LocalDate.parse("2024-05-26")
//        println(endLocalDate)


        // given
        val now: Instant = Clock.System.now()
        val endLocalDateTime: LocalDateTime = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))
// or shorter
//        val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val budget = Budget(
            sum = 1000.0,
            endLocalDateTime = endLocalDateTime
        )
//        every { expenseRepository.setBudget(budget) }.returns(budget)

        // when
        val actualBudget = businessLogic.setBudget(budget)

        // then
        assertEquals(expected = budget, actual = actualBudget)
    }

    @Test
    fun should_set_budget_and_get_for_current_month() {
        // given
        // setting budget until the end of june
        val endOfJune = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))

        val budget = Budget(
            sum = 1000.0,
            endLocalDateTime = endOfJune
        )

        // when
        val insertedBudget = businessLogic.setBudget(budget)
        val fetchedBudget = businessLogic.getCurrentBudget()

        // then
        assertEquals(expected = budget, actual = fetchedBudget)
    }

    // business logic layer test
    @Test
    fun new_updated_test_should_set_the_budget() {
        // given
        val endOfJune = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))
// or shorter
//        val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val budget = Budget(
            sum = 1000.0,
            endLocalDateTime = endOfJune
        )

        // when
        val actualBudget = businessLogic.setBudget(budget)

        // then
        assertEquals(expected = budget, actual = actualBudget)
    }


    // business logic layer test 2
    @Test
    fun should_set_budget_then_add_expense_to_the_db_with_subtracting_from_the_current_months_budget() {
        // given
        // set budget until end of june
        val endOfJune = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))
        val budget = Budget(
            sum = 1000.0,
            endLocalDateTime = endOfJune
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
        assertEquals(
            expected = budget.sum - expense.sum,
            actual = currentBudgetAfterAddingExpense.sum
        )
    }

    @Test
    fun should_get_daily_available_amount() {
        // given
        // set budget until end of june
        val endOfJune = LocalDateTime(LocalDate(2024, Month.JUNE, 3), LocalTime(23, 59, 59))
        val budget = Budget(
            sum = 1000.0,
            endLocalDateTime = endOfJune
        )
        val actualBudget = businessLogic.setBudget(budget)

        // first test: should get correct daily available amount
        val dailyAvailableAmountBeforeAddingExpense = businessLogic.getDailyAvailableAmount()
        println("HKe dailyAvailableAmountBeforeAddingExpense ${dailyAvailableAmountBeforeAddingExpense}")

        // add expense
        val expense = Expense(
            sum = 200.0,
        )

        // when
        val actualAddedExpense = businessLogic.enterExpense(expense)
        val dailyAvailableAmountAfterAddingExpense = businessLogic.getDailyAvailableAmount()
        println("HKe dailyAvailableAmountAfterAddingExpense ${dailyAvailableAmountAfterAddingExpense}")

        // then
        assertEquals(expected = expense, actual = actualAddedExpense)
        assertEquals(expected = 333, actual = dailyAvailableAmountBeforeAddingExpense)
        assertEquals(expected = 267, actual = dailyAvailableAmountAfterAddingExpense)
    }

    @Test
    fun testCase1() {
        val endLocalDateTime: LocalDateTime = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))
        val budgetSum: Double = 550.0
        val expectedDailyAvailableAmount: Int = 21
        testCase(
            endLocalDateTime,
            budgetSum,
            expectedDailyAvailableAmount,
        )
    }

    // this scenario should actually write something like "less than 1 EUR daily"
    @Test
    fun testCase2() {
        val endLocalDateTime: LocalDateTime = LocalDateTime(LocalDate(2024, Month.JUNE, 26), LocalTime(23, 59, 59))
        val budgetSum: Double = 1.0
        val expectedDailyAvailableAmount: Int = 0
        testCase(
            endLocalDateTime,
            budgetSum,
            expectedDailyAvailableAmount,
        )
    }

    @Test
    fun testCase3_time_part_of_the_endLocalDateTime_is_earlier_than_NOW_and_should_still_consider_this_as_1_day_of_budget_i_e_should_ignore_the_time_part_and_only_calculate_based_on_the_days() {
        val endLocalDateTime: LocalDateTime = LocalDateTime(LocalDate(2024, Month.JUNE, 1), LocalTime(1, 59, 59))
        val budgetSum: Double = 550.0
        val expectedDailyAvailableAmount: Int = 550
        testCase(
            endLocalDateTime,
            budgetSum,
            expectedDailyAvailableAmount,
        )
    }

    private fun testCase(
        endLocalDateTime: LocalDateTime,
        budgetSum: Double,
        expectedDailyAvailableAmount: Int,
    ) {
        // given
        // set budget
        val budget = Budget(
            sum = budgetSum,
            endLocalDateTime = endLocalDateTime
        )
        val actualBudget = businessLogic.setBudget(budget)

        // first test: should get correct daily available amount
        val dailyAvailableAmount = businessLogic.getDailyAvailableAmount()
        println("HKe dailyAvailableAmountBeforeAddingExpense ${dailyAvailableAmount}")

        // then
        assertEquals(expected = expectedDailyAvailableAmount, actual = dailyAvailableAmount)

    }
}