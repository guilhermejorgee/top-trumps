package com.gui.top.trumps.core.game.application

import com.gui.top.trumps.core.game.application.repository.CategoryRepository
import com.gui.top.trumps.core.game.domain.Category
import org.springframework.stereotype.Service

@Service
class CategoryService(
    val categoryRepository: CategoryRepository
) {

    fun createCategory(name: String, description: String): Category {
        val category = Category(name, description)
        return categoryRepository.save(category)
    }

}