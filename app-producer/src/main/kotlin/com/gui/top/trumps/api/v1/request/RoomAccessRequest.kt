package com.gui.top.trumps.api.v1.request

import jakarta.validation.constraints.NotBlank

data class RoomAccessRequest(
    @field:NotBlank
    val playerId: String
)