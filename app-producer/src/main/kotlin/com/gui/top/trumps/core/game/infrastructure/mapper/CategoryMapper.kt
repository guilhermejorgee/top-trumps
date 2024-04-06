package com.gui.top.trumps.core.game.infrastructure.mapper

import com.gui.top.trumps.core.game.domain.Category
import com.gui.top.trumps.core.game.domain.User
import com.gui.top.trumps.core.game.infrastructure.entities.CategoryEntity
import com.gui.top.trumps.core.game.infrastructure.entities.UserEntity

class CategoryMapper {
    companion object{
        fun fromEntityToDomain(categoryEntity: CategoryEntity): Category {
            return Category(categoryEntity.id, categoryEntity.name, categoryEntity.description)
        }
        fun fromDomainToEntity(category: Category): CategoryEntity {
            return CategoryEntity(category.id, category.name, category.description)
        }
    }
}