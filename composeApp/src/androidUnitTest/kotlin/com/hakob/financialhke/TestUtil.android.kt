package com.hakob.financialhke

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.hakob.financialhke.database.Database

internal actual fun testDbConnection(): SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    .also { Database.Schema.create(it) }
