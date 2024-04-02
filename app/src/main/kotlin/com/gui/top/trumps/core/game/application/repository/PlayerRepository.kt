package com.gui.top.trumps.core.game.application.repository

import com.gui.top.trumps.core.game.domain.Player
import java.util.*

interface PlayerRepository {
    fun save(player : Player): Player
    fun findById(idPlayer: String): Optional<Player>
}