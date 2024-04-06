package com.hakob.financialhke.db

import EntryDataSource
import com.hakob.financialhke.database.EntryEntity
import com.hakob.financialhke.database.EntryQueries
import com.hakob.financialhke.domain.Entry

class SqlDelightEntryDataSource(
    val queries: EntryQueries
) : EntryDataSource {
    val getQuery = queries.getAllEntries()
    override fun insertEntry(entry: Entry) {
        queries.insertEntry(entry.id.toLong(), entry.sum)
    }

    override fun getEntry(id: Int): List<Entry> {
        return queries.getAllEntries().executeAsList().map { it.toDomainEntry() }
    }

    private fun EntryEntity.toDomainEntry() =
        Entry(
            this.id.toInt(),
            this.sum!!
        )
}