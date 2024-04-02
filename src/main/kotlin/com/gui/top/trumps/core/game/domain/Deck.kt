package com.gui.top.trumps.core.game.domain

import com.gui.top.trumps.core.common.domain.AggregateRoot

class Deck(
    val id: String,
    val name: String,
    val category: Category,
    val cards: Set<Card>
) : AggregateRoot() {



}