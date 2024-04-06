package com.hakob.financialhke.koin

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.hakob.financialhke.database.Database
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(Database.Schema, "test.db") }

}


