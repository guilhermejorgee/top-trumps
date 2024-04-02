package com.gui.top.trumps.core.common.domain

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EventHandler(
    private val eventDispatch: EventDispatch
) {

    @Async
    @EventListener
    fun eventListener(event: Event){
        eventDispatch.sent(event)
    }

}