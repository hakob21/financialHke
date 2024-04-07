package com.hakob.financialhke

import EntryDataSource
import com.hakob.financialhke.domain.Entry

class Greeting(
    val entryDataSource: EntryDataSource,
) {
    private val platform = getPlatform()

    fun greet(): String {
        doDbThings()
        return "Hello, ${platform.name}!"
    }

    fun doDbThings() {
//        val database = Database(driver)

//        println(playerQueries.getAllEntries())
        // [HockeyPlayer(15, "Ryan Getzlaf")]

        // this gets stored (at least in case of IOS) permanently. on the first run the row is inserted in the DB, but next runs it fails
//      // because the primary key id is already present in the DB. can change the ID and run again every time
        entryDataSource.insertEntry(Entry(1, 2.0))
        println(entryDataSource.getAllEntries(1))
        // [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]

    }
}