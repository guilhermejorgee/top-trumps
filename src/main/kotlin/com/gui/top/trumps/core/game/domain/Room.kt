package com.gui.top.trumps.game.domain

import arrow.core.Either
import com.github.f4b6a3.ulid.UlidCreator
import com.gui.top.trumps.core.common.domain.AggregateRoot
import com.gui.top.trumps.core.game.domain.Deck
import com.gui.top.trumps.core.game.domain.Match
import com.gui.top.trumps.core.game.domain.Player
import com.gui.top.trumps.core.game.domain.error.DomainError
import java.util.UUID

class Room(
    val id: String,
    val players: MutableSet<Player>,
    var status: RoomStatus,
    var slots: Int,
    val pass: String,
    var matchs: MutableList<Match>
): AggregateRoot() {

    fun addPlayer(player: Player): Either<DomainError, Room> {
        return when {
            players.contains(player) -> Either.Left(DomainError.PlayerAlreadyInRoom)
            slots == 0 -> Either.Left(DomainError.FullRoom)
            else -> {
                players.add(player)
                slots--
                if (this.slots == 0) this.status = RoomStatus.READY_TO_START
                Either.Right(this)
            }
        }
    }

    fun initMatch(deck: Deck): Either<DomainError, Match>{
        if (status != RoomStatus.READY_TO_START) return Either.Left(DomainError.RoomNotReady)
        val matchCreated = Match.create(players, id, deck)
        this.matchs.add(matchCreated)
        this.status = RoomStatus.MATCH_IN_PROGRESS
        return Either.Right(matchCreated)
    }

    fun finishMatch(idMatch: String): Either<DomainError, Match>{
        val match = this.matchs.find { it.id == idMatch } ?: return Either.Left(DomainError.MatchNotExists)
        match.defineWinner()
        return Either.Right(match)
    }



    companion object {
        fun create(player: Player, slots: Int): Either<DomainError, Room> {
            if (slots < 2 || slots > 8) {
                return Either.Left(DomainError.SlotQuantityInvalid)
            }
            return Either.Right(
                Room(
                    id = UlidCreator.getUlid().toString(),
                    players = mutableSetOf(player),
                    status = RoomStatus.WAITING_FOR_PLAYERS,
                    slots = slots,
                    pass = UUID.randomUUID().toString(),
                    mutableListOf()
                )
            )
        }
    }

}

enum class RoomStatus {
    WAITING_FOR_PLAYERS, READY_TO_START, MATCH_IN_PROGRESS
}
