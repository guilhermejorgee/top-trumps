package com.gui.top.trumps.core.game.domain

import arrow.core.Either
import arrow.core.getOrElse
import com.github.f4b6a3.ulid.UlidCreator
import com.gui.top.trumps.core.common.domain.AggregateRoot
import com.gui.top.trumps.core.common.domain.Event
import com.gui.top.trumps.core.game.domain.error.DomainError
import com.gui.top.trumps.core.game.domain.events.UserInitRoomEvent
import java.util.*


class User(
    val id: String,
    val name: String
): AggregateRoot {

    private val events: MutableList<Event> = mutableListOf()

    fun initRoom(slots: Int): Either<DomainError, Room>{
        val room = Room.create(this, slots).getOrElse { return Either.Left(it) }
        this.addEvents(UserInitRoomEvent(
            roomId = room.id,
            roomPass = room.pass,
            userId = id,
            slots = slots
        ))
        return Either.Right(room)
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }

    constructor(name: String) : this(id = UlidCreator.getUlid().toString(), name)


}