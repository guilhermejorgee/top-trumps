package com.gui.top.trumps.core.game.infrastructure.entities

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class CardEntity(
    val id: String,

    @Indexed
    val deckId: String,

    val attributes: Map<String, String>
)