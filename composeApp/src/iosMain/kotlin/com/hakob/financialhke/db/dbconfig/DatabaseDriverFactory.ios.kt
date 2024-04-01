package com.hakob.financialhke.db.dbconfig

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.hakob.financialhke.database.Database

actual fun createDatabaseHke(): Database {
    return Database(NativeSqliteDriver(Database.Schema, "test.db"))
}

// don't need this for current impl. Depending on how will refactor the sqldelight initialization, might need it later
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        TODO("Not yet implemented")
    }
}