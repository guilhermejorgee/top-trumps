package com.gui.top.trumps.core.game.domain

import arrow.core.Either
import arrow.core.getOrElse
import com.github.f4b6a3.ulid.UlidCreator
import com.gui.top.trumps.core.common.domain.AggregateRoot
import com.gui.top.trumps.core.common.domain.Event
import com.gui.top.trumps.core.game.domain.error.DomainError
import com.gui.top.trumps.core.game.domain.events.RoomAddUserEvent
import com.gui.top.trumps.core.game.domain.events.RoomFinishMatchEvent
import com.gui.top.trumps.core.game.domain.events.RoomInitMatchEvent
import com.gui.top.trumps.core.game.domain.events.RoomNextMatchRoundEvent
import com.gui.top.trumps.core.game.domain.vo.PlayerNewCard
import java.util.Optional
import java.util.UUID

class Room(
    val id: String,
    val users: MutableSet<User>,
    var status: RoomStatus,
    val slots: Int,
    val pass: String,
    val matchs: MutableList<Match>
): AggregateRoot {

    private val events: MutableList<Event> = mutableListOf()

    fun addUser(user: User): Either<DomainError, Room> {
        return when {
            users.contains(user) -> Either.Left(DomainError.PlayerAlreadyInRoom)
            users.size == slots -> Either.Left(DomainError.FullRoom)
            else -> {
                users.add(user)
                if (users.size == slots) this.status = RoomStatus.READY_TO_START
                this.addEvents(RoomAddUserEvent(
                    roomId = id,
                    roomStatus = status.name,
                    roomPass = pass,
                    userId = user.id,
                    userName = user.name,
                    remainingSlots = slots
                ))
                Either.Right(this)
            }
        }
    }

    fun initMatch(deck: Deck): Either<DomainError, Match>{
        if (status != RoomStatus.READY_TO_START) return Either.Left(DomainError.RoomNotReady)
        val matchCreated = Match.create(users, id, deck)
        this.matchs.add(matchCreated)
        this.status = RoomStatus.MATCH_IN_PROGRESS
        this.addEvents(RoomInitMatchEvent(
            roomId = id,
            roomStatus = status.name,
            roomPass = pass,
            matchId = matchCreated.id,
            players = matchCreated.players,
            deckId = deck.id
        ))
        return Either.Right(matchCreated)
    }

    fun finishMatch(idMatch: String): Either<DomainError, Match>{
        val matchOptional = findMatch(idMatch)
        if(matchOptional.isEmpty){
            return Either.Left(DomainError.MatchNotExists)
        }
        val match = matchOptional.get()
        match.defineWinner().getOrElse {
            return Either.Left(it)
        }

        this.addEvents(RoomFinishMatchEvent(
            roomId = id,
            roomStatus = status.name,
            roomPass = pass,
            matchId = match.id,
            matchStatus = match.status.name,
            players = match.players,
            winner = match.winner!!
        ))
        return Either.Right(match)
    }

    fun startNextMatchRound(idMatch: String, newPlayers: List<PlayerNewCard>): Either<DomainError, Match>{
        val matchOptional = findMatch(idMatch)
        if(matchOptional.isEmpty){
            return Either.Left(DomainError.MatchNotExists)
        }

        val match = matchOptional.get()

        match.nextRound(newPlayers).getOrElse {
            return Either.Left(it)
        }

        this.addEvents(RoomNextMatchRoundEvent(
            roomId = id,
            roomStatus = status.name,
            roomPass = pass,
            matchId = match.id,
            players = match.players,
            round = match.round
        ))

        return Either.Right(match)
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

    override fun addEvents(event: Event) {
        this.events.add(event)
    }

    override fun clearEvents() {
        this.events.clear()
    }

    override fun listEvents(): List<Event> {
        return this.events
    }

}

enum class RoomStatus {
    WAITING_FOR_PLAYERS, READY_TO_START, MATCH_IN_PROGRESS
}
