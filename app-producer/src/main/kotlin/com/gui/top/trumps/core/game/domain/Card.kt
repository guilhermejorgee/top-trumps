package com.gui.top.trumps.core.game.domain

import arrow.core.Either
import com.github.f4b6a3.ulid.UlidCreator
import com.gui.top.trumps.core.game.domain.error.DomainError

class Card(
    val id: String,
    val attributes: Map<String, String>
) {

    companion object{
        fun create(attributes: Map<String, String>): Either<DomainError, Card> {
            if(attributes.size !in 4..5){
                return Either.Left(DomainError.InvalidNumberAttributes)
            }
            return Either.Right(
                Card(
                id = UlidCreator.getUlid().toString(),
                attributes = attributes
                )
            )
        }
    }

}