package com.gui.top.trumps.core.game.infrastructure.entities

import com.gui.top.trumps.core.game.domain.Category
import jakarta.persistence.*

@Entity
@Table(name = "DECKS")
data class DeckEntity(

    @Id
    @Column(name = "ID")
    val id: String,

    @Column(name = "NAME")
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    val category: CategoryEntity
)