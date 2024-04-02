package com.gui.top.trumps.api.v1.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class RoomCreateRequest(
    @field:NotBlank
    val playerId: String,
    @field:NotNull
    val slots: Int
)