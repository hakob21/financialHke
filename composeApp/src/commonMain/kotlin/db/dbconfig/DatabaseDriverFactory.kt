package db.dbconfig

import app.cash.sqldelight.db.SqlDriver
import org.hakob.financialhke.database.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}
expect fun createDatabaseHke(): Database

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver: SqlDriver = driverFactory.createDriver()
    val database = Database(driver)

    // Do more work with the database (see below).
    return database
}