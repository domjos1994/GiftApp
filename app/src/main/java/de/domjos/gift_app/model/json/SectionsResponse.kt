package de.domjos.gift_app.model.json

import de.domjos.gift_app.model.SectionSummary

data class SectionsResponse(val data: Array<SectionSummary>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SectionsResponse

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
