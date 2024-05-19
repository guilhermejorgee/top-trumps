package com.gui.top.trumps.core.game.infrastructure.entities

import com.gui.top.trumps.core.game.infrastructure.converter.StringListConverter
import jakarta.persistence.*

@Entity
@Table(name = "PLAYERS")
data class PlayerEntity(

    @Id
    val id: String,
    @Column(name = "NAME")
    val name: String,
    @Column(name = "MATCH_ID")
    val matchId: String,
    @Convert(converter = StringListConverter::class)
    @Column(name = "CARDS")
    val cardsId: List<String>
)