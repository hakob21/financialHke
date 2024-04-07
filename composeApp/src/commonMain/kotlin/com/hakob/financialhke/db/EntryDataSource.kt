import com.hakob.financialhke.database.EntryEntity
import com.hakob.financialhke.domain.Entry

interface EntryDataSource {
    fun insertEntry(entry: Entry)
    fun getAllEntries(id: Int): List<Entry>
}