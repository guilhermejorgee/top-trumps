package com.gui.top.trumps.core.game.infrastructure

import com.gui.top.trumps.core.game.application.repository.CategoryRepository
import com.gui.top.trumps.core.game.domain.Category
import com.gui.top.trumps.core.game.infrastructure.entities.CategoryEntity
import com.gui.top.trumps.core.game.infrastructure.mapper.CategoryMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CategoryRepositoryImpl(
    private val repositoryJpa: CategorySpringRepository
): CategoryRepository {

    override fun findById(id: String): Optional<Category> {
        val categoryEntity = repositoryJpa.findById(id)
        if(categoryEntity.isEmpty){
            return Optional.empty()
        }
        return Optional.of(CategoryMapper.fromEntityToDomain(categoryEntity.get()))
    }

    override fun save(category: Category): Category {
        val categoryEntity = CategoryMapper.fromDomainToEntity(category)
        return CategoryMapper.fromEntityToDomain(repositoryJpa.save(categoryEntity))
    }

}

interface CategorySpringRepository : JpaRepository<CategoryEntity, String> {}