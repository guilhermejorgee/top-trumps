package com.gui.top.trumps.api.v1.request

import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    val name: String
)