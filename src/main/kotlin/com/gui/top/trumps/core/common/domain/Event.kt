package com.gui.top.trumps.core.common.domain

import java.util.*

abstract class Event(
    val aggregateId: String,
    val occurredOn: Date,
    val eventVersion: Int
)