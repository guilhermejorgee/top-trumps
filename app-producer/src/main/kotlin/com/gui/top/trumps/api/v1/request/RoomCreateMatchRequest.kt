package com.gui.top.trumps.api.v1.request

import jakarta.validation.constraints.NotBlank

data class RoomCreateMatchRequest(
    @field:NotBlank
    val deckId: String
)
