package com.gui.top.trumps.core.game.application

import arrow.core.Either
import arrow.core.getOrElse
import com.gui.top.trumps.core.common.application.Loggable
import com.gui.top.trumps.core.common.domain.EventManager
import com.gui.top.trumps.core.game.application.error.ApplicationError
import com.gui.top.trumps.core.game.application.repository.CategoryRepository
import com.gui.top.trumps.core.game.application.repository.DeckRepository
import com.gui.top.trumps.core.game.domain.Deck

class DeckService(
    private val repositoryDeck: DeckRepository,
    private val repositoryCategory: CategoryRepository,
    private val eventManager: EventManager
): Loggable {

    fun createDeck(name: String, categoryId: String, cardsAttributes: List<Map<String,String>>): Either<ApplicationError, Deck>{

        val category = repositoryCategory.findById(categoryId)

        if(category.isEmpty){
            return Either.Left(ApplicationError.UnprocessableEntity)
        }

        val deck = Deck.create(name, category.get(), cardsAttributes).getOrElse {
            logger.error(it.message)
            return Either.Left(ApplicationError.UnexpectedError)
        }

        val deckPersisted = repositoryDeck.save(deck)

        eventManager.publish(deck)

        return Either.Right(deckPersisted)
    }

    fun getDeck(deckId: String): Either<ApplicationError, Deck>{
        val deck = repositoryDeck.findById(deckId)
        if(deck.isEmpty){
            logger.info("Entity Not Found")
            Either.Left(ApplicationError.EntityNotFound)
        }
        return Either.Right(deck.get())
    }

}