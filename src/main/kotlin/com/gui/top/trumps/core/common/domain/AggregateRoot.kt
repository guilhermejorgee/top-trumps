package com.gui.top.trumps.core.common.domain

abstract class AggregateRoot {

    val events: MutableSet<Event> = mutableSetOf()

    fun addEvents(event: Event){
        this.events.add(event)
    }

    fun clearEvents(){
        this.events.clear()
    }

}