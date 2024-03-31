package com.gui.top.trumps.core.game.application

import arrow.core.Either
import arrow.core.getOrElse
import com.gui.top.trumps.core.common.application.Loggable
import com.gui.top.trumps.core.game.application.error.ApplicationError
import com.gui.top.trumps.core.game.application.repository.DeckRepository
import com.gui.top.trumps.core.game.application.repository.RoomRepository
import com.gui.top.trumps.core.game.domain.Match
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MatchService(
    val repositoryRoom: RoomRepository,
    val repositoryDeck: DeckRepository
): Loggable {

    @Transactional
    fun createMatch(idRoom: String, deckId: String): Either<ApplicationError, Match>{
        val room = repositoryRoom.findById(idRoom)
        if(room.isEmpty){
            logger.info("Entity Not Found")
            Either.Left(ApplicationError.EntityNotFound)
        }

        val deck = repositoryDeck.findById(deckId)
        if(deck.isEmpty){
            logger.info("Entity Not Found")
            Either.Left(ApplicationError.EntityNotFound)
        }

        val match = room.get().initMatch(deck.get()).getOrElse {
            logger.error(it.message)
            return Either.Left(ApplicationError.UnexpectedError)
        };

        return Either.Right(match)
    }

    fun endMatch(idRoom: String, idMatch: String){
        val room = repositoryRoom.findById(idRoom)
        if(room.isEmpty){
            logger.info("Entity Not Found")
            Either.Left(ApplicationError.EntityNotFound)
        }

        room.get().finishMatch(idMatch)

    }


}