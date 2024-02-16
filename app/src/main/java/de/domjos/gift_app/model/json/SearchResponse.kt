package de.domjos.gift_app.model.json

import de.domjos.gift_app.model.Meta
import de.domjos.gift_app.model.Search

data class SearchResponse(val query: String, val data: Search, val meta: Meta)
