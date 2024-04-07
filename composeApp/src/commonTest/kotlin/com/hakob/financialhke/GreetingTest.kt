package com.hakob.financialhke

import com.hakob.financialhke.db.repository.EntryRepository
import com.hakob.financialhke.domain.Entry
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
    private val entryRepository: EntryRepository = mock(classOf<EntryRepository>())

    private val greeting: Greeting = Greeting(entryRepository)

    @AfterTest
    fun tearDown() {
    }

    @Test
    fun greet() {
        val expected = listOf(Entry(1, 1.1))
        every { entryRepository.getAllEntries() }.returns(listOf(Entry(1, 1.1)))
        val actual = entryRepository.getAllEntries()

        assertEquals(expected, actual)
//        Assertion().assertEquals(greeting.greet(), "Hello, Android!")
    }
}