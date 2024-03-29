import domain.Entry

interface EntryDataSource {
    suspend fun insertEntry(entry: Entry)
}