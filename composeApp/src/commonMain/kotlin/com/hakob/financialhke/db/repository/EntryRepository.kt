package com.hakob.financialhke.db.repository

import com.hakob.financialhke.domain.Entry

interface EntryRepository {
    fun insertEntry(entry: Entry)
    fun getAllEntries(): List<Entry>
}