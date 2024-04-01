package com.hakob.financialhke.db

import EntryDataSource
import database.EntryEntity
import database.EntryQueries
import domain.Entry

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