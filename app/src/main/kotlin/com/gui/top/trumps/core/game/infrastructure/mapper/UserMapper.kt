package com.gui.top.trumps.core.game.infrastructure.mapper

import com.gui.top.trumps.core.game.domain.User
import com.gui.top.trumps.core.game.infrastructure.entities.UserEntity

class UserMapper {
    companion object{
        fun fromEntityToDomain(userEntity: UserEntity): User {
            return User(userEntity.id, userEntity.name)
        }
        fun fromDomainToEntity(user: User): UserEntity {
            return UserEntity(user.id, user.name)
        }
    }
}