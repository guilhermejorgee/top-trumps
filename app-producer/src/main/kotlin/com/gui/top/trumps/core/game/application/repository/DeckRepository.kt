package com.gui.top.trumps.core.game.application.repository

import com.gui.top.trumps.core.game.domain.Deck
import java.util.*

interface DeckRepository {
    fun findById(id: String): Optional<Deck>
    fun save(deck: Deck): Deck
}