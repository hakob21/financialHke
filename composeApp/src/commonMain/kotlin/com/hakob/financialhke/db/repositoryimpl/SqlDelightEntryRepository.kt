package com.hakob.financialhke.db.repositoryimpl

import com.hakob.financialhke.db.repository.EntryRepository
import com.hakob.financialhke.database.EntryEntity
import com.hakob.financialhke.database.EntryQueries
import com.hakob.financialhke.domain.Entry

class SqlDelightEntryRepository(
    private val queries: EntryQueries
) : EntryRepository {

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