package de.domjos.gift_app.model.json

import de.domjos.gift_app.model.ChapterSummary

data class ChaptersResponse(val data: Array<ChapterSummary>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChaptersResponse

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
