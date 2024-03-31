package com.gui.top.trumps.core.game.application.repository

import com.gui.top.trumps.game.domain.Room
import java.util.*

interface RoomRepository {
    fun save(room : Room): Room
    fun findByPass(idPassRoom: String): Optional<Room>
    fun findById(idRoom: String): Optional<Room>
}