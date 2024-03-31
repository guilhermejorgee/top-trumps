package com.gui.top.trumps.core.game.infrastructure.entities

import com.gui.top.trumps.game.domain.Category

data class DeckEntity(
    val id: String,
    val name: String,
    val category: Category
) {

}