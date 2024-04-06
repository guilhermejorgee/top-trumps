package com.gui.top.trumps.api.v1.response

import com.gui.top.trumps.core.game.domain.Card

data class DeckCreateResponse(
    val id: String,
    val name: String,
    val category: String,
)