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

        // this gets stored (at least in case of IOS) permanently. on the first run the row is inserted in the DB, but next runs it fails
//      // because the primary key id is already present in the DB. can change the ID and run again every time
        playerQueries.insertEntry(Long.MAX_VALUE - 2, 3.4)
        println(playerQueries.getAllEntries().executeAsList())
        // [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]

    }
}