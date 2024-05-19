package com.gui.top.trumps.core.game.infrastructure.mapper

import com.gui.top.trumps.core.game.infrastructure.entities.RoomEntity
import com.gui.top.trumps.core.game.domain.Room

class RoomMapper {
    companion object {
        fun fromEntityToDomain(roomEntity: RoomEntity): Room {
            return Room(
                id = roomEntity.id,
                users = roomEntity.users.map { UserMapper.fromEntityToDomain(it) }.toMutableSet(),
                status = roomEntity.status,
                slots = roomEntity.slots,
                pass = roomEntity.pass,
                matchs = roomEntity.matchs.map { MatchMapper.convertEntityToMatch(it) }.toMutableList()
            )
        }

        fun fromDomainToEntity(room: Room): RoomEntity {
            return RoomEntity(
                id = room.id,
                users = room.users.map{ UserMapper.fromDomainToEntity(it) }.toMutableSet(),
                status = room.status,
                slots = room.slots,
                pass = room.pass,
                matchs = room.matchs.map { MatchMapper.convertMatchToEntity(it) }.toMutableList()
            )
        }
    }
}