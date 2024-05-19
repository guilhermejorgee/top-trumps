package com.gui.top.trumps.api.v1.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateDeckRequest(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val categoryId: String,
    @field:NotNull
    val cards: List<Map<String,String>>
)