package com.gui.top.trumps.core.game.infrastructure.mapper

import com.gui.top.trumps.core.game.infrastructure.entities.CardEntity
import com.gui.top.trumps.core.game.infrastructure.entities.DeckEntity

data class DeckEntities(val deck: DeckEntity, val card: Set<CardEntity>)
