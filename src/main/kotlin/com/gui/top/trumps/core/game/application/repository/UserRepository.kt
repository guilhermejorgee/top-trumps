package com.gui.top.trumps.core.game.application.repository

import com.gui.top.trumps.core.game.domain.User
import java.util.*

interface UserRepository {
    fun save(user : User): User
    fun findById(userId: String): Optional<User>
}