package com.gui.top.trumps.core.game.domain.events

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gui.top.trumps.core.common.domain.Event

data class UserInitRoomEvent(
    val eventName: String = "INIT_ROOM",
    val roomId: String,
    val roomPass: String,
    val userId: String,
    val slots: Int
) : Event{

    override fun toJson(): String {
        return jacksonObjectMapper().writeValueAsString(this)
    }

}