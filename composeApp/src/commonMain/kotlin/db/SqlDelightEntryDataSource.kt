package db

import EntryDataSource
import database.EntryQueries
import domain.Entry

class SqlDelightEntryDataSource(db: EntryQueries): EntryDataSource {
    val insertQuery = db.insertEntry(Long.MAX_VALUE, 3.4)
    val getQuery = db.getAllEntries()
    override suspend fun insertEntry(entry: Entry) {
        val get = getQuery
        println(get.executeAsList().get(0))
    }
}