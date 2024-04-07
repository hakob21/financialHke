package com.hakob.financialhke.db.dbconfig

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.hakob.financialhke.database.Database

// don't need this for current impl. Depending on how will refactor the sqldelight initialization, might need it later
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        TODO("Not yet implemented")
    }
}
