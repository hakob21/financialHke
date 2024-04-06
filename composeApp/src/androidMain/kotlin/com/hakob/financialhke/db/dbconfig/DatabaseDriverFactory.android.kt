package com.hakob.financialhke.db.dbconfig

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.hakob.financialhke.applicationContext
import com.hakob.financialhke.database.Database

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "test.db")
    }
}

actual fun createDatabaseHke(): Database {

    return Database(AndroidSqliteDriver(Database.Schema, getContextHke(), "test.db"))
}

fun getContextHke(): Context {
    return applicationContext
}
