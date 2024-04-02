package com.gui.top.trumps.core.game.application

import arrow.core.Either
import arrow.core.getOrElse
import com.gui.top.trumps.core.common.application.Loggable
import com.gui.top.trumps.core.game.application.error.ApplicationError
import com.gui.top.trumps.core.game.application.inputs.PlayerNewCardsInput
import com.gui.top.trumps.core.game.application.repository.DeckRepository
import com.gui.top.trumps.core.game.application.repository.RoomRepository
import com.gui.top.trumps.core.game.domain.Match
import com.gui.top.trumps.core.game.domain.vo.PlayerNewCard
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MatchService(
    private val repositoryRoom: RoomRepository,
    private val repositoryDeck: DeckRepository
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
        }

        return Either.Right(match)
    }

    @Transactional
    fun endMatch(idRoom: String, idMatch: String): Either<ApplicationError, Match>{
        val room = repositoryRoom.findById(idRoom)
        if(room.isEmpty){
            logger.info("Entity Not Found")
            return Either.Left(ApplicationError.EntityNotFound)
        }
        val matchFinished = room.get().finishMatch(idMatch).getOrElse {
            logger.error(it.message)
            return Either.Left(ApplicationError.UnexpectedError)
        }
        return Either.Right(matchFinished)
    }

    @Transactional
    fun newMatchRound(
        idRoom: String,
        idMatch: String,
        players: List<PlayerNewCardsInput>
    ): Either<ApplicationError, Match> {

        val room = repositoryRoom.findById(idRoom)

        if(room.isEmpty){
            logger.info("Entity Not Found")
            Either.Left(ApplicationError.EntityNotFound)
        }

        val matchWithNewRound = room.get()
            .startNextMatchRound(idMatch, players.map { PlayerNewCard(it.id, it.cardsId) })
            .getOrElse {
                logger.error(it.message)
                return Either.Left(ApplicationError.UnexpectedError)
            }

        return Either.Right(matchWithNewRound)
    }


}