package com.gui.top.trumps.core.game.domain.events

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gui.top.trumps.core.common.domain.Event

data class RoomAddUserEvent(
    val eventName: String = "ADD_USER",
    val roomId: String,
    val roomPass: String,
    val roomStatus: String,
    val userId: String,
    val userName: String,
    val remainingSlots: Int
) : Event{

    override fun toJson(): String {
        return jacksonObjectMapper().writeValueAsString(this)
    }

}