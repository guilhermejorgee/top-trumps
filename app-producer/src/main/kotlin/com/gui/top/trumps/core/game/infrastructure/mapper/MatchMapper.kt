package com.gui.top.trumps.core.game.infrastructure.mapper

import com.gui.top.trumps.core.game.domain.Card
import com.gui.top.trumps.core.game.domain.Match
import com.gui.top.trumps.core.game.domain.Player
import com.gui.top.trumps.core.game.infrastructure.entities.MatchEntity
import com.gui.top.trumps.core.game.infrastructure.entities.PlayerEntity

class MatchMapper {
    companion object{
        fun convertMatchToEntity(match: Match): MatchEntity {
            val playerEntities = match.players.map { convertPlayerToPlayerEntity(it, match.id) }.toSet()
            return MatchEntity(
                id = match.id,
                deckId = match.deckId,
                round = match.round,
                status = match.status,
                roomId = match.roomId,
                winner = match.winner,
                players = playerEntities
            )
        }

        fun convertEntityToMatch(matchEntity: MatchEntity): Match {
            val players = matchEntity.players.map { convertPlayerEntityToPlayer(it) }.toSet()
            return Match(
                id = matchEntity.id,
                deckId = matchEntity.deckId,
                round = matchEntity.round,
                status = matchEntity.status,
                roomId = matchEntity.roomId,
                winner = matchEntity.winner,
                players = players
            )
        }

        private fun convertPlayerToPlayerEntity(player: Player, matchId: String): PlayerEntity {
            return PlayerEntity(
                id = player.id,
                name = player.name,
                matchId = matchId,
                cardsId = player.cards
            )
        }

        private fun convertPlayerEntityToPlayer(playerEntity: PlayerEntity): Player {
            return Player(
                id = playerEntity.id,
                name = playerEntity.name,
                cards = playerEntity.cardsId
            )
        }
    }
}