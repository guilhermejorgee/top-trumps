package com.gui.top.trumps.core.game.domain.events

import com.gui.top.trumps.core.common.domain.Event
import java.util.*

class MatchCreatedEvent(
    aggregateId: String,
    val name: String
) : Event(
    aggregateId = aggregateId,
    occurredOn = Date(),
    eventVersion = 1
)