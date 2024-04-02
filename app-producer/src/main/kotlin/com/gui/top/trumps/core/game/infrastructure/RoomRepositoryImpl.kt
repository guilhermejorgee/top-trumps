package com.gui.top.trumps.core.game.infrastructure

import com.gui.top.trumps.core.game.application.repository.RoomRepository
import com.gui.top.trumps.core.game.infrastructure.entities.RoomEntity
import com.gui.top.trumps.core.game.infrastructure.mapper.RoomMapper
import com.gui.top.trumps.core.game.domain.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class RoomRepositoryImpl(
    private val repository: RoomSpringRepository
): RoomRepository {

    override fun save(room: Room): Room {
        val roomEntity = repository.save(RoomMapper.fromDomainToEntity(room))
        return RoomMapper.fromEntityToDomain(roomEntity)
    }

    override fun findByPass(idPassRoom: String): Optional<Room> {
        val roomEntity = repository.findByPass(idPassRoom)
        if(roomEntity.isPresent){
            return Optional.of(RoomMapper.fromEntityToDomain(roomEntity.get()))
        }
        return Optional.empty()
    }

    override fun findById(idRoom: String): Optional<Room> {
        val roomEntity = repository.findById(idRoom)
        if(roomEntity.isPresent){
            return Optional.of(RoomMapper.fromEntityToDomain(roomEntity.get()))
        }
        return Optional.empty()
    }

}

interface RoomSpringRepository : JpaRepository<RoomEntity, String> {
    fun findByPass(pass: String): Optional<RoomEntity>
}