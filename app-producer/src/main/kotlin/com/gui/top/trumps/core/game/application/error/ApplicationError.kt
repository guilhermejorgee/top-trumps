package com.gui.top.trumps.core.game.application.error

sealed class ApplicationError(
    val message: String
){
    data object EntityNotFound : ApplicationError("Entity was not found.")
    data object UnexpectedError : ApplicationError("An unexpected error occurred.")
    data object UnprocessableEntity: ApplicationError("Was unable to process instructions")
}