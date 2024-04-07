package com.hakob.financialhke

import EntryRepository
import com.hakob.financialhke.domain.Entry

class Greeting(
    val entryRepository: EntryRepository,
) {
    private val platform = getPlatform()

    fun greet(): String {
        doDbThings()
        return "Hello, ${platform.name}!"
    }

    fun doDbThings() {
        // this gets stored (at least in case of IOS) permanently. on the first run the row is
        // inserted in the DB, but next runs it fails
        // because the primary key id is already present in the DB.
        // can change the ID and run again every time
        val entry = Entry(2, 2.0)
        entryRepository.insertEntry(entry)
        check(entryRepository.getAllEntries().contains(entry))
    }
}