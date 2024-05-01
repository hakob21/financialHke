package com.hakob.financialhke

import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.db.repositoryimpl.ExpenseRepository
import com.hakob.financialhke.domain.Entry
import com.hakob.financialhke.domain.Expense
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.every
import io.mockative.mock
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
class GreetingTest {

    @Mock
    private val expenseRepository: ExpenseRepositoryInterface = mock(classOf<ExpenseRepositoryInterface>())

    private val greeting: Greeting = Greeting(expenseRepository)

    @AfterTest
    fun tearDown() {
    }

    @Test
    fun greet() {
        val expected = listOf(Expense(sum = 3.0))
        every { expenseRepository.expenses() }.returns(listOf(Expense(sum = 3.0)))
        val actual = expenseRepository.expenses()

        assertEquals(expected, actual)
//        Assertion().assertEquals(greeting.greet(), "Hello, Android!")
    }
}