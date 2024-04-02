package com.gui.top.trumps.core.common.domain

interface AggregateRoot {
    fun addEvents(event: Event)
    fun clearEvents()
    fun listEvents(): List<Event>
}