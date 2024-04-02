package com.gui.top.trumps.core.game.application.inputs

data class CreateRoomInput(
    val playerId: String,
)

data class PlayerInput(val id: String, val name: String)
