package com.gui.top.trumps.core.game.application.repository

import com.gui.top.trumps.core.game.domain.Category
import java.util.*

interface CategoryRepository {
    fun findById(id: String): Optional<Category>
    fun save(category: Category): Category
}