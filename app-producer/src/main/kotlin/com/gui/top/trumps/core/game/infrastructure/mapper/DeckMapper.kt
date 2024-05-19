package com.gui.top.trumps.core.game.infrastructure.mapper

import com.gui.top.trumps.core.game.domain.Card
import com.gui.top.trumps.core.game.domain.Category
import com.gui.top.trumps.core.game.domain.Deck
import com.gui.top.trumps.core.game.infrastructure.entities.CardEntity
import com.gui.top.trumps.core.game.infrastructure.entities.CategoryEntity
import com.gui.top.trumps.core.game.infrastructure.entities.DeckEntity

class DeckMapper {
    companion object{
        fun fromEntityToDomain(deck: DeckEntity, cards: Set<CardEntity>): Deck{
            return Deck(
                id = deck.id,
                name = deck.name,
                category = Category(deck.category.id, deck.category.name, deck.category.description),
                cards.map { Card(it.id, it.attributes) }.toSet()
            )
        }
        fun fromDomainToEntities(deck: Deck): DeckEntities{
            val deckEntity = DeckEntity(
                id = deck.id,
                name = deck.name,
                category = CategoryEntity(deck.category.id.toString(), deck.category.name, deck.category.description)
            )
            val cardEntity = deck.cards.map { CardEntity(it.id, deck.id, it.attributes) }.toSet()

            return DeckEntities(deckEntity, cardEntity)
        }
    }
}