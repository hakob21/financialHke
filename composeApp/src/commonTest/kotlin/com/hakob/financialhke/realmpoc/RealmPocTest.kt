package com.hakob.financialhke.realmpoc

import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.db.repositoryimpl.ExpenseRepository
import com.hakob.financialhke.domain.Expense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import com.hakob.financialhke.db.repodomain.Expense as RealmExpense
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RealmPocTest {
    private lateinit var expenseRepositoryInterface: ExpenseRepositoryInterface

    @BeforeTest
    fun setup() = runTest {

        val configuration = RealmConfiguration.create(schema = setOf(RealmExpense::class))
        val realm = Realm.open(configuration)

        expenseRepositoryInterface = ExpenseRepository(realm)
        expenseRepositoryInterface.deleteAllExpenses()
    }

    @Test
    fun `should insert an Entry record to the DB and fetch all entries where single record is the inserted Entry`() {
        runTest {
            // given
            val expenseToInsert = Expense(sum = 1.0)

            // when
            expenseRepositoryInterface.addExpense(expenseToInsert)
            val actualExpenses: List<Expense> = expenseRepositoryInterface.expenses()

            // then
            assertEquals(listOf(expenseToInsert), actualExpenses)
        }
    }
}


//class SqlDelightGeneralTest {
//
//    private lateinit var entryRepository: EntryRepository
//
//    @BeforeTest
//    fun setup() = runTest {
//        entryRepository =
//            SqlDelightEntryRepository(
//                EntryQueries(
//                    sqlDriverForTesting()
//                )
//            )
//    }
//
//    @Test
//    fun `should insert an Entry record to the DB and fetch all entries where single record is the inserted Entry`() =
//        runTest {
//            // given
//            val entryRecordToInsert = Entry(1, 2.2)
//
//            // when
//            entryRepository.insertEntry(entryRecordToInsert)
//            // keep here in order to intentionally fail the test for e.g. testing of CI pipeline
//            // val actualEntryEntity: List<Entry> = emptyList()
//            val actualEntryEntity: List<Entry> = entryRepository.getAllEntries()
//
//            // then
//            assertEquals(listOf(entryRecordToInsert), actualEntryEntity)
//        }
//
////    @Test
////    fun `Select Item by Id Success`() = runTest {
////        val breeds = dbHelper.selectAllItems().first()
////        val firstBreed = breeds.first()
////        assertNotNull(
////            dbHelper.selectById(firstBreed.id),
////            "Could not retrieve Breed by Id"
////        )
////    }
////
////    @Test
////    fun `Update Favorite Success`() = runTest {
////        val breeds = dbHelper.selectAllItems().first()
////        val firstBreed = breeds.first()
////        dbHelper.updateFavorite(firstBreed.id, true)
////        val newBreed = dbHelper.selectById(firstBreed.id).first().first()
////        assertNotNull(
////            newBreed,
////            "Could not retrieve Breed by Id"
////        )
////        assertTrue(
////            newBreed.favorite,
////            "Favorite Did Not Save"
////        )
////    }
////
////    @Test
////    fun `Delete All Success`() = runTest {
////        dbHelper.insertBreed("Poodle")
////        dbHelper.insertBreed("Schnauzer")
////        assertTrue(dbHelper.selectAllItems().first().isNotEmpty())
////        dbHelper.deleteAll()
////
////        assertTrue(
////            dbHelper.selectAllItems().first().count() == 0,
////            "Delete All did not work"
////        )
////    }
//}
