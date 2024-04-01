package com.hakob.financialhke

import EntryDataSource
import database.EntryEntity
import database.EntryQueries
import com.hakob.financialhke.db.SqlDelightEntryDataSource
import com.hakob.financialhke.domain.Entry
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SqlDelightTest {

    private lateinit var dbHelper: EntryDataSource

//    private suspend fun DatabaseHelper.insertBreed(name: String) {
//        insertBreeds(listOf(name))
//    }

    @BeforeTest
    fun setup() = runTest {
        dbHelper = SqlDelightEntryDataSource(
            EntryQueries(
                testDbConnection()
            )
        )
    }

    @Test
    fun `should get inserted entry`() = runTest {
        dbHelper.insertEntry(Entry(1, 2.2))
        val actualEntryEntity: EntryEntity = dbHelper.getEntry(1)

        assertEquals<EntryEntity>(EntryEntity(1, 2.2), actualEntryEntity)
    }

//    @Test
//    fun `Select Item by Id Success`() = runTest {
//        val breeds = dbHelper.selectAllItems().first()
//        val firstBreed = breeds.first()
//        assertNotNull(
//            dbHelper.selectById(firstBreed.id),
//            "Could not retrieve Breed by Id"
//        )
//    }
//
//    @Test
//    fun `Update Favorite Success`() = runTest {
//        val breeds = dbHelper.selectAllItems().first()
//        val firstBreed = breeds.first()
//        dbHelper.updateFavorite(firstBreed.id, true)
//        val newBreed = dbHelper.selectById(firstBreed.id).first().first()
//        assertNotNull(
//            newBreed,
//            "Could not retrieve Breed by Id"
//        )
//        assertTrue(
//            newBreed.favorite,
//            "Favorite Did Not Save"
//        )
//    }
//
//    @Test
//    fun `Delete All Success`() = runTest {
//        dbHelper.insertBreed("Poodle")
//        dbHelper.insertBreed("Schnauzer")
//        assertTrue(dbHelper.selectAllItems().first().isNotEmpty())
//        dbHelper.deleteAll()
//
//        assertTrue(
//            dbHelper.selectAllItems().first().count() == 0,
//            "Delete All did not work"
//        )
//    }
}
