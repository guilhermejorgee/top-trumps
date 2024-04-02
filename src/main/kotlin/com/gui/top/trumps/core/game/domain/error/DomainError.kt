package com.gui.top.trumps.core.game.domain.error

sealed class DomainError(
    val message: String
){
    data object SlotQuantityInvalid : DomainError("The number of slots entered is invalid.")
    data object PlayerAlreadyInRoom : DomainError("The Player is already in the game room.")
    data object FullRoom : DomainError("The room is already full.")
    data object RoomNotReady : DomainError("The room is not ready to start the match.")
    data object MatchNotExists : DomainError("The match does not exist in the room.")
    data object UnableDetermineWinner : DomainError("Unable to determine a winner.")
    data object UnableNextRound : DomainError("Unable to advance to the next round.")
}