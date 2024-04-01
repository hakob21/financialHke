import database.EntryEntity
import domain.Entry

interface EntryDataSource {
    suspend fun insertEntry(entry: Entry)
    suspend fun getEntry(id: Int): EntryEntity
}