package com.gui.top.trumps.core.common.domain

abstract class AggregateRoot {

    private val events: MutableList<Event> = mutableListOf()

    fun addEvents(event: Event){
        this.events.add(event)
    }

    fun clearEvents(){
        this.events.clear()
    }

}