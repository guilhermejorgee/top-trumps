package com.gui.top.trumps.core.game.domain

import com.gui.top.trumps.core.common.domain.AggregateRoot
import com.gui.top.trumps.core.common.domain.Event

class Deck(
    val id: String,
    val name: String,
    val category: Category,
    val cards: Set<Card>
) : AggregateRoot {

    private val events: MutableList<Event> = mutableListOf()

    fun separateCards(qtdPlayers: Int): List<List<Card>> {
        val cardsPerPlayer = this.cards.size / qtdPlayers
        return this.cards.chunked(cardsPerPlayer)
    }

    override fun addEvents(event: Event) {
        this.events.add(event)
    }

    override fun clearEvents() {
        this.events.clear()
    }

    override fun listEvents(): List<Event> {
        return this.events
    }


}