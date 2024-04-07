package com.hakob.financialhke.db

import EntryDataSource
import com.hakob.financialhke.database.EntryEntity
import com.hakob.financialhke.database.EntryQueries
import com.hakob.financialhke.domain.Entry

class SqlDelightEntryDataSource(
    private val queries: EntryQueries
) : EntryDataSource {

    override fun insertEntry(entry: Entry) {
        queries.insertEntry(entry.id.toLong(), entry.sum)
    }

    override fun getAllEntries(): List<Entry> {
        return queries.getAllEntries().executeAsList().map { it.toDomainEntry() }
    }

    private fun EntryEntity.toDomainEntry() =
        Entry(
            this.id.toInt(),
            this.sum!!
        )
}