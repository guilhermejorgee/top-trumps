package com.gui.top.trumps.core.game.domain

import com.github.f4b6a3.ulid.Ulid
import com.github.f4b6a3.ulid.UlidCreator
import com.gui.top.trumps.core.common.domain.AggregateRoot
import com.gui.top.trumps.core.common.domain.Event

class Category(
    val id: String,
    val name: String,
    val description: String
) {
    constructor(name: String, description: String) : this(id = UlidCreator.getUlid().toString(), name, description)
}