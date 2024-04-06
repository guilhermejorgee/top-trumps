package com.gui.top.trumps.api.v1.request

import jakarta.validation.constraints.NotBlank

data class CreateCategoryRequest(
    @NotBlank
    val name: String,
    @NotBlank
    val description: String
)