package com.gui.top.trumps.core.game.domain

import arrow.core.Either
import arrow.core.getOrElse
import com.gui.top.trumps.game.domain.Room
import com.gui.top.trumps.core.game.domain.error.DomainError
import java.util.*


class Player(
    val id: String,
    val name: String,
    var cards: Set<Card>?
) {

    fun initRoom(slots: Int): Either<DomainError, Room>{
        val room = Room.create(this, slots).getOrElse { return Either.Left(it) }
        return Either.Right(room)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }


}