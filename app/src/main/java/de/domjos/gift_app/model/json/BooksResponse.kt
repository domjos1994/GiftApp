package de.domjos.gift_app.model.json

import de.domjos.gift_app.model.Book

data class BooksResponse(val data: Array<Book>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BooksResponse

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
