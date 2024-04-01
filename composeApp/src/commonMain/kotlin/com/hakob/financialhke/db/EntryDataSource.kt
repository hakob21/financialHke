import database.EntryEntity
import domain.Entry

interface EntryDataSource {
    fun insertEntry(entry: Entry)
    fun getEntry(id: Int): EntryEntity
}