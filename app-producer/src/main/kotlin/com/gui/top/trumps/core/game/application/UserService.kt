package com.gui.top.trumps.core.game.application

import arrow.core.Either
import com.gui.top.trumps.core.common.application.Loggable
import com.gui.top.trumps.core.game.application.error.ApplicationError
import com.gui.top.trumps.core.game.application.repository.UserRepository
import com.gui.top.trumps.core.game.domain.User

class UserService(
    private val userRepository: UserRepository
): Loggable {

    fun createUser(name: String): Either<ApplicationError, User>{
        val user = User(name)
        val userPersisted = userRepository.save(user)
        return Either.Right(userPersisted)
    }

    fun getUser(userId: String): Either<ApplicationError, User>{
        val user = userRepository.findById(userId)
        if(user.isEmpty){
            logger.info("Entity Not Found")
            return Either.Left(ApplicationError.EntityNotFound)
        }
        return Either.Right(user.get())
    }
}