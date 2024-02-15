package de.domjos.gift_app.model.json

import de.domjos.gift_app.model.BibleSummary

data class BibleSummaryResponse(val data: Array<BibleSummary>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BibleSummaryResponse

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
