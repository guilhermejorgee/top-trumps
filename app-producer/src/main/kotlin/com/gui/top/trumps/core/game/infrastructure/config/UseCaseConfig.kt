package com.gui.top.trumps.core.game.infrastructure.config

import com.gui.top.trumps.core.common.application.UnitOfWork
import com.gui.top.trumps.core.common.domain.EventManager
import com.gui.top.trumps.core.game.application.*
import com.gui.top.trumps.core.game.application.repository.CategoryRepository
import com.gui.top.trumps.core.game.application.repository.DeckRepository
import com.gui.top.trumps.core.game.application.repository.RoomRepository
import com.gui.top.trumps.core.game.application.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig(
    private val categoryRepository: CategoryRepository,
    private val repositoryDeck: DeckRepository,
    private val repositoryCategory: CategoryRepository,
    private val repositoryRoom: RoomRepository,
    private val userRepository: UserRepository,
    private val eventManager: EventManager,
    private val unitOfWork: UnitOfWork
) {

    @Bean
    fun categoryService(): CategoryService {
        return CategoryService(categoryRepository)
    }

    @Bean
    fun deckService(): DeckService {
        return DeckService(repositoryDeck, repositoryCategory, eventManager)
    }

    @Bean
    fun matchService(): MatchService {
        return MatchService(repositoryRoom, repositoryDeck, eventManager, unitOfWork)
    }

    @Bean
    fun roomService(): RoomService {
        return RoomService(repositoryRoom, userRepository, eventManager, unitOfWork)
    }

    @Bean
    fun userService(): UserService {
        return UserService(userRepository)
    }


}