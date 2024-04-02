package com.gui.top.trumps.core.game.domain

import arrow.core.Either
import com.github.f4b6a3.ulid.UlidCreator
import com.gui.top.trumps.core.common.domain.AggregateRoot
import com.gui.top.trumps.core.game.domain.error.DomainError
import com.gui.top.trumps.core.game.domain.vo.PlayerNewCard
import java.util.Optional
import java.util.UUID

class Room(
    val id: String,
    val users: MutableSet<User>,
    var status: RoomStatus,
    var slots: Int,
    val pass: String,
    val matchs: MutableList<Match>
): AggregateRoot() {

    fun addUser(user: User): Either<DomainError, Room> {
        return when {
            users.contains(user) -> Either.Left(DomainError.PlayerAlreadyInRoom)
            slots == 0 -> Either.Left(DomainError.FullRoom)
            else -> {
                users.add(user)
                slots--
                if (this.slots == 0) this.status = RoomStatus.READY_TO_START
                Either.Right(this)
            }
        }
    }

    fun initMatch(deck: Deck): Either<DomainError, Match>{
        if (status != RoomStatus.READY_TO_START) return Either.Left(DomainError.RoomNotReady)
        val matchCreated = Match.create(users, id, deck)
        this.matchs.add(matchCreated)
        this.status = RoomStatus.MATCH_IN_PROGRESS
        return Either.Right(matchCreated)
    }

    fun finishMatch(idMatch: String): Either<DomainError, Match>{
        val match = findMatch(idMatch)
        if(match.isEmpty){
            return Either.Left(DomainError.MatchNotExists)
        }
        match.get().defineWinner()
        return Either.Right(match.get())
    }

    fun startNextMatchRound(idMatch: String, newPlayers: List<PlayerNewCard>): Either<DomainError, Match>{
        val match = findMatch(idMatch)
        if(match.isEmpty){
            return Either.Left(DomainError.MatchNotExists)
        }
        match.get().nextRound(newPlayers)
        return Either.Right(match.get())
    }

    private fun findMatch(idMatch: String): Optional<Match>{
        return Optional.ofNullable(this.matchs.find { it.id == idMatch })
    }



    companion object {
        fun create(player: User, slots: Int): Either<DomainError, Room> {
            if (slots < 2 || slots > 8) {
                return Either.Left(DomainError.SlotQuantityInvalid)
            }
            return Either.Right(
                Room(
                    id = UlidCreator.getUlid().toString(),
                    users = mutableSetOf(player),
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
