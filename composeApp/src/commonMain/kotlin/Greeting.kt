import org.hakob.financialhke.database.Database
import shared.createDatabaseHke

class Greeting(
) {
    private val platform = getPlatform()

    fun greet(): String {
        val db = createDatabaseHke()
        doDbThings(db)
        return "Hello, ${platform.name}!"
    }

    fun doDbThings(database: Database) {
//        val database = Database(driver)
        val playerQueries = database.entryQueries

//        println(playerQueries.getAllEntries())
        // [HockeyPlayer(15, "Ryan Getzlaf")]

        playerQueries.insertEntry(Long.MAX_VALUE - 1, 3.4)
        println(playerQueries.getAllEntries().executeAsList())
        // [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]

    }
}