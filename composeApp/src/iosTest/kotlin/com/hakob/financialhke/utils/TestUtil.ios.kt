package com.hakob.financialhke.utils

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.hakob.financialhke.database.Database

internal actual fun sqlDriverForTesting(): SqlDriver {
    return inMemoryDriver(Database.Schema)
}