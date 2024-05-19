package com.gui.top.trumps.core.game.domain

import arrow.core.Either
import arrow.core.getOrElse
import com.github.f4b6a3.ulid.UlidCreator
import com.gui.top.trumps.core.common.domain.AggregateRoot
import com.gui.top.trumps.core.common.domain.Event
import com.gui.top.trumps.core.game.domain.error.DomainError

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

    companion object{
        fun create(name: String, category: Category, cardsAttributes: List<Map<String,String>>): Either<DomainError, Deck>{
            if(cardsAttributes.size < 32){
                return Either.Left(DomainError.InvalidNumberCards)
            }

            val cards = createCard(cardsAttributes).getOrElse {
                return Either.Left(it)
            }

            return Either.Right(Deck(
                id = UlidCreator.getUlid().toString(),
                name = name,
                category = category,
                cards = cards
            ))
        }
        private fun createCard(cardsAttributes: List<Map<String, String>>): Either<DomainError, Set<Card>>{
            val cards = cardsAttributes.map { Card.create(it) }
            val failures = cards.filterIsInstance<Either.Left<DomainError>>()
            return if (failures.isNotEmpty()) {
                failures.first()
            } else {
                Either.Right(cards.map { (it as Either.Right<Card>).value }.toSet())
            }
        }
    }


}