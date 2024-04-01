package com.hakob.financialhke.koin

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.hakob.financialhke.applicationContext
import org.hakob.financialhke.database.Database
import org.koin.dsl.module


actual val platformModule = module {

    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = Database.Schema,
            context = applicationContext, // from android startup library
            name = "test.db"
        )
    }
}

