package com.gui.top.trumps.core.game.domain.events

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gui.top.trumps.core.common.domain.Event
import com.gui.top.trumps.core.game.domain.Player

data class RoomNextMatchRoundEvent(
    val eventName: String = NameEvents.NEXT_ROUND_MATCH.name,
    val roomId: String,
    val roomPass: String,
    val roomStatus: String,
    val matchId: String,
    val players: Set<Player>,
    val round: Int
) : Event{

    override fun toJson(): String {
        return jacksonObjectMapper().writeValueAsString(this)
    }

}