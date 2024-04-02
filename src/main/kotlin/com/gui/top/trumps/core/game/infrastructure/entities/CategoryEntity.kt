package com.gui.top.trumps.core.game.infrastructure.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "CATEGORIES")
data class CategoryEntity(

    @Id
    @Column(name = "ID")
    val id: String,

    @Column(name = "NAME")
    val name: String,

    @Column(name = "DESCRIPTION")
    val description: String
)