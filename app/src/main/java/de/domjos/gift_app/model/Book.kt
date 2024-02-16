package de.domjos.gift_app.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index

@Entity(
    tableName = "books",
    indices = [
        Index(value=["id"], orders = [Index.Order.ASC], name = "pk_book_id", unique = true)
    ],
    inheritSuperIndices = false,
    primaryKeys = ["id"]
)
data class Book(
    var id: String, var bibleId: String, var abbreviation: String,
    var name: String, var nameLong: String,
    @Ignore var chapters: Array<ChapterSummary>) {

    constructor(
        id: String, bibleId: String, abbreviation: String,
        name: String, nameLong: String)
            : this(id, bibleId, abbreviation, name, nameLong, arrayOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false
        if (bibleId != other.bibleId) return false
        if (abbreviation != other.abbreviation) return false
        if (name != other.name) return false
        if (nameLong != other.nameLong) return false
        return chapters.contentEquals(other.chapters)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + bibleId.hashCode()
        result = 31 * result + abbreviation.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + nameLong.hashCode()
        result = 31 * result + chapters.contentHashCode()
        return result
    }
}
