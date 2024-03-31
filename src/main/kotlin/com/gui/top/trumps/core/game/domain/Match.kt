package com.gui.top.trumps.core.game.domain

import com.github.f4b6a3.ulid.UlidCreator
import java.lang.IllegalArgumentException

class Match(
    val id: String,
    val players: Set<Player>,
    val deckId: String,
    val round: Int,
    var status: MatchStatus,
    val roomId: String,
    var winner: String?
) {

    fun defineWinner() {
        val winnerCandidate = players.find { it.cards?.size == 32 }
            ?: players.maxByOrNull { it.cards?.size ?: 0 }

        winnerCandidate?.let {
            this.winner = it.name
            this.status = MatchStatus.FINISHED
        } ?: throw IllegalArgumentException("Unable to determine a winner.")
    }

    companion object {
        fun create(players: Set<Player>, roomId: String, deck: Deck): Match {
            val playersWithCards = separateCards(deck, players)
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
        private fun separateCards(deck: Deck, players: Set<Player>): Set<Player> {
            val cardsPerPlayer = deck.cards.size / players.size
            val chunkedCards = deck.cards.chunked(cardsPerPlayer)

            val updatedPlayers = players.zip(chunkedCards).map { (player, cards) ->
                player.apply { this.cards = cards.toSet() }
            }.toSet()

            return updatedPlayers
        }
    }



}

enum class MatchStatus {
    IN_PROGRESS, FINISHED
}