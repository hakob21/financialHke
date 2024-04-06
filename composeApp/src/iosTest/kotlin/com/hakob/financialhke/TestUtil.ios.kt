package com.hakob.financialhke

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.hakob.financialhke.database.Database

internal actual fun testDbConnection(): SqlDriver {
    return inMemoryDriver(Database.Schema)
}