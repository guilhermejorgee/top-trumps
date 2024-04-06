package com.gui.top.trumps.core.game.application

import arrow.core.Either
import arrow.core.getOrElse
import com.gui.top.trumps.core.common.application.Loggable
import com.gui.top.trumps.core.common.domain.EventManager
import com.gui.top.trumps.core.game.application.error.ApplicationError
import com.gui.top.trumps.core.game.application.repository.CategoryRepository
import com.gui.top.trumps.core.game.application.repository.DeckRepository
import com.gui.top.trumps.core.game.domain.Deck
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeckService(
    private val repositoryDeck: DeckRepository,
    private val repositoryCategory: CategoryRepository,
    private val eventManager: EventManager
): Loggable {

    @Transactional
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

}