package com.gui.top.trumps.api.v1.response

data class RoomAccessResponse(
    val id: String,
    val users: List<String>,
    val status: String,
    val slots: Int,
    val availableSlots: Int
)