package com.gui.top.trumps.core.game.application

import arrow.core.Either
import arrow.core.getOrElse
import com.gui.top.trumps.core.common.application.Loggable
import com.gui.top.trumps.core.common.application.UnitOfWork
import com.gui.top.trumps.core.common.domain.EventManager
import com.gui.top.trumps.core.game.application.error.ApplicationError
import com.gui.top.trumps.core.game.application.repository.RoomRepository
import com.gui.top.trumps.core.game.application.repository.UserRepository
import com.gui.top.trumps.core.game.domain.Room

class RoomService(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val eventManager: EventManager,
    private val unitOfWork: UnitOfWork
): Loggable {

    fun createRoom(userId: String, slots: Int): Either<ApplicationError, Room>{
        val userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty){
            logger.info("Entity Not Found")
            return Either.Left(ApplicationError.EntityNotFound)
        }
        val user = userOptional.get()

        val room = user.initRoom(slots).getOrElse {
            logger.error(it.message)
            return Either.Left(ApplicationError.UnexpectedError)
        }

        val roomPersisted = roomRepository.save(room)

        eventManager.publish(room, user)

        return Either.Right(roomPersisted)
    }

    fun accessRoom(userId: String, pass: String): Either<ApplicationError, Room>{
        return unitOfWork.runTransaction {
            val user = userRepository.findById(userId);
            if(user.isEmpty){
                logger.info("Entity Not Found")
                return@runTransaction Either.Left(ApplicationError.EntityNotFound)
            }

            val room = roomRepository.findByPass(pass)

            if(room.isEmpty){
                logger.info("Entity Not Found")
                return@runTransaction Either.Left(ApplicationError.EntityNotFound)
            }

            room.get().addUser(user.get()).getOrElse {
                logger.error(it.message)
                return@runTransaction Either.Left(ApplicationError.UnexpectedError)
            }

            val roomPersisted = roomRepository.save(room.get())

            eventManager.publish(room.get(), user.get())

            return@runTransaction Either.Right(roomPersisted)
        }

    }



}