package com.gui.top.trumps.core.game.infrastructure.mapper

import com.gui.top.trumps.core.game.infrastructure.entities.PlayerEntity
import com.gui.top.trumps.core.game.infrastructure.entities.RoomEntity

data class RoomEntities(val deck: RoomEntity, val players: Set<PlayerEntity>)