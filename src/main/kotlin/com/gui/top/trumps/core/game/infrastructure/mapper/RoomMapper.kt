package com.gui.top.trumps.core.game.infrastructure.mapper

import com.gui.top.trumps.core.game.infrastructure.entities.RoomEntity
import com.gui.top.trumps.game.domain.Room

class RoomMapper {
    companion object {
        fun fromEntityToDomain(roomEntity: RoomEntity): Room {
            return Room(
                id = roomEntity.id,
                players = roomEntity.players.map(PlayerMapper::toDomain).toMutableSet(),
                status = roomEntity.status,
                slots = roomEntity.slots,
                pass = roomEntity.pass,
                matchs = roomEntity.match?.let(MatchMapper::toDomain)
            )
        }

        fun fromDomainToEntity(room: Room): RoomEntity {
            return RoomEntity(
                id = room.id,
                players = room.players.map(PlayerMapper::fromDomain).toMutableSet(),
                status = room.status,
                slots = room.slots,
                pass = room.pass,
                match = room.matchs?.let(MatchMapper::fromDomain)
            )
        }
    }
}