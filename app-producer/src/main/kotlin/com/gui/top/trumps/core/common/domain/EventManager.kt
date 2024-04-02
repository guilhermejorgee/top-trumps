package com.gui.top.trumps.core.common.domain

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventManager(
    private val eventPublisher: ApplicationEventPublisher
) {

    fun publish(vararg aggregateRoots: AggregateRoot) {
        aggregateRoots.flatMap { it.listEvents() }
            .forEach { eventPublisher.publishEvent(it) }
    }
}