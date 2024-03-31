package com.gui.top.trumps.core.game.infrastructure.entities

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class PlayerCardEntity(
    val id: String,

    @Indexed
    val matchId: String,

    val attributes: Map<String, String>
)