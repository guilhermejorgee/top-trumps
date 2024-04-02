package com.gui.top.trumps.core.game.infrastructure

import com.gui.top.trumps.core.game.application.repository.UserRepository
import com.gui.top.trumps.core.game.domain.User
import com.gui.top.trumps.core.game.infrastructure.entities.UserEntity
import com.gui.top.trumps.core.game.infrastructure.mapper.UserMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl(
    private val repository: UserSpringRepository
): UserRepository {
    override fun save(user: User): User {
        val userEntity = repository.save(UserMapper.fromDomainToEntity(user))
        return UserMapper.fromEntityToDomain(userEntity)
    }

    override fun findById(userId: String): Optional<User> {
        val userEntity = repository.findById(userId)
        if(userEntity.isPresent){
            return Optional.of(UserMapper.fromEntityToDomain(userEntity.get()))
        }
        return Optional.empty()
    }

}

interface UserSpringRepository : JpaRepository<UserEntity, String> {}