package com.hakob.financialhke.db

import EntryDataSource
import com.hakob.financialhke.database.EntryEntity
import com.hakob.financialhke.database.EntryQueries
import com.hakob.financialhke.domain.Entry

class SqlDelightEntryDataSource(
    val db: EntryQueries
) : EntryDataSource {
    val getQuery = db.getAllEntries()
    override fun insertEntry(entry: Entry) {
        db.insertEntry(entry.id.toLong(), entry.sum)
    }

    override fun getEntry(id: Int): EntryEntity {
        return getQuery.executeAsList().get(0)
    }
}