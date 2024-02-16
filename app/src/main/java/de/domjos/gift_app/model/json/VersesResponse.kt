package de.domjos.gift_app.model.json

import de.domjos.gift_app.model.VerseSummary

data class VersesResponse(val data: Array<VerseSummary>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VersesResponse

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
