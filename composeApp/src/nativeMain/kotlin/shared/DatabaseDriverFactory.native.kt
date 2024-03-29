package shared

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.hakob.financialhke.database.Database

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "test.db")
    }
}

actual fun createDatabaseHke(): Database {
    return Database(NativeSqliteDriver(Database.Schema, "test.db"))
}