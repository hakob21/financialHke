import database.EntryEntity
import db.SqlDelightEntryDataSource
import io.mockative.Mock
import io.mockative.any
import io.mockative.classOf
import io.mockative.coEvery
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
    private val entryDataSource: EntryDataSource = mock(classOf<EntryDataSource>())

    private val greeting: Greeting = Greeting(entryDataSource)

    @AfterTest
    fun tearDown() {
    }

    @Test
    fun greet() {
        val expected = EntryEntity(1, 1.1)
        every { entryDataSource.getEntry(any()) }.returns(EntryEntity(1, 1.1))
        val actual = entryDataSource.getEntry(1)

        assertEquals(expected, actual)
//        Assertion().assertEquals(greeting.greet(), "Hello, Android!")
    }
}