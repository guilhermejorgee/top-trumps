package com.gui.top.trumps.core.common.domain

interface EventDispatch {
    fun send(event: Event)
}