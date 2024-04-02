package com.gui.top.trumps.core.game.domain.events

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gui.top.trumps.core.common.domain.Event
import com.gui.top.trumps.core.game.domain.Player

data class RoomFinishMatchEvent(
    val eventName: String = NameEvents.FINISH_MATCH.name,
    val roomId: String,
    val roomStatus: String,
    val roomPass: String,
    val matchId: String,
    val matchStatus: String,
    val players: Set<Player>,
    val winner: String
) : Event{

    override fun toJson(): String {
        return jacksonObjectMapper().writeValueAsString(this)
    }

}