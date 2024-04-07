import com.hakob.financialhke.domain.Entry

interface EntryDataSource {
    fun insertEntry(entry: Entry)
    fun getAllEntries(): List<Entry>
}