package com.gui.top.trumps.core.game.domain

import arrow.core.Either
import com.github.f4b6a3.ulid.UlidCreator
import com.gui.top.trumps.core.game.domain.error.DomainError
import com.gui.top.trumps.core.game.domain.vo.PlayerNewCard

class Match(
    val id: String,
    val players: Set<Player>,
    val deckId: String,
    var round: Int,
    var status: MatchStatus,
    val roomId: String,
    var winner: String?
) {

    fun defineWinner(): Either<DomainError, Match> {
        val winnerCandidate = players.find { it.cards.size == 32 }
            ?: players.maxByOrNull { it.cards.size ?: 0 }

        winnerCandidate?.let {
            this.winner = it.name
            this.status = MatchStatus.FINISHED
        } ?: return Either.Left(DomainError.UnableDetermineWinner)

        return Either.Right(this)
    }

    fun nextRound(newPlayers: List<PlayerNewCard>): Either<DomainError, Match>{
        if(this.status != MatchStatus.IN_PROGRESS){
            return Either.Left(DomainError.UnableNextRound)
        }
        val newCardsMap = newPlayers.associateBy({ it.id }, { it.cardsId })

        this.players.forEach { playerOld ->
            newCardsMap[playerOld.id]?.let { newCards ->
                playerOld.cards = newCards
            }
        }

        if(this.players.any { it.cards.size >= 32 }) return Either.Left(DomainError.UnableNextRound)

        this.round++

        return Either.Right(this)
    }

    companion object {
        fun create(users: Set<User>, roomId: String, deck: Deck): Match {
            val playersWithCards = distributeCards(deck, users)
            return Match(
                id = UlidCreator.getUlid().toString(),
                players = playersWithCards,
                deckId = deck.id,
                round = 0,
                status = MatchStatus.IN_PROGRESS,
                roomId = roomId,
                null
            )
        }

        private fun distributeCards(deck: Deck, users: Set<User>): Set<Player> {
            val cardsPerPlayer = deck.separateCards(users.size)

            val usersCreated = users.zip(cardsPerPlayer).map { (user, cards) ->
                Player(user.id, user.name, cards.map { it.id })
            }.toSet()

            return usersCreated
        }
    }



}

enum class MatchStatus {
    IN_PROGRESS, FINISHED
}