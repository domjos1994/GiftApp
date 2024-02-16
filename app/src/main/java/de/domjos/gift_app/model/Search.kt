package de.domjos.gift_app.model

data class Search(
    var query: String, var limit: Int, var offset: Int, var total: Int, var verseCount: Int,
    var verses: Array<SearchVerse>, var passages: Array<Passage>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Search

        if (query != other.query) return false
        if (limit != other.limit) return false
        if (offset != other.offset) return false
        if (total != other.total) return false
        if (verseCount != other.verseCount) return false
        if (!verses.contentEquals(other.verses)) return false
        return passages.contentEquals(other.passages)
    }

    override fun hashCode(): Int {
        var result = query.hashCode()
        result = 31 * result + limit
        result = 31 * result + offset
        result = 31 * result + total
        result = 31 * result + verseCount
        result = 31 * result + verses.contentHashCode()
        result = 31 * result + passages.contentHashCode()
        return result
    }
}
