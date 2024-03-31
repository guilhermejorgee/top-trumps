package com.gui.top.trumps.core.game.application

import arrow.core.Either
import arrow.core.getOrElse
import com.gui.top.trumps.core.common.application.Loggable
import com.gui.top.trumps.core.game.application.error.ApplicationError
import com.gui.top.trumps.core.game.application.repository.PlayerRepository
import com.gui.top.trumps.core.game.application.repository.RoomRepository
import com.gui.top.trumps.game.domain.Room
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    val roomRepository: RoomRepository,
    val playerRepository: PlayerRepository
): Loggable {

    @Transactional
    fun createRoom(playerId: String, slots: Int): Either<ApplicationError, Room>{
        val playerOptional = playerRepository.findById(playerId);
        if(playerOptional.isEmpty){
            logger.info("Entity Not Found")
            return Either.Left(ApplicationError.EntityNotFound)
        }
        val player = playerOptional.get()

        val room = player.initRoom(slots).getOrElse {
            logger.error(it.message)
            return Either.Left(ApplicationError.UnexpectedError)
        }

        val roomPersisted = roomRepository.save(room)

        return Either.Right(roomPersisted)
    }

    @Transactional
    fun accessRoom(playerId: String, pass: String): Either<ApplicationError, Room>{
        val player = playerRepository.findById(playerId);
        if(player.isEmpty){
            logger.info("Entity Not Found")
            return Either.Left(ApplicationError.EntityNotFound)
        }

        val room = roomRepository.findByPass(pass)

        if(room.isEmpty){
            logger.info("Entity Not Found")
            return Either.Left(ApplicationError.EntityNotFound)
        }

        room.get().addPlayer(player.get()).getOrElse {
            logger.error(it.message)
            return Either.Left(ApplicationError.UnexpectedError)
        };

        val roomPersisted = roomRepository.save(room.get())

        return Either.Right(roomPersisted)
    }



}