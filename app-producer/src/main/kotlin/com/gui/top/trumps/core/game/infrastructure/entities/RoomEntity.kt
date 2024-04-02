package com.gui.top.trumps.core.game.infrastructure.entities

import com.gui.top.trumps.core.game.domain.Match
import com.gui.top.trumps.core.game.domain.RoomStatus
import jakarta.persistence.*

@Entity
@Table(name = "ROOMS", indexes = [Index(name = "idx_room_pass", columnList = "pass")])
data class RoomEntity(

    @Id
    @Column(name = "ID")
    val id: String,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinTable(
        name = "ROOM_USERS",
        joinColumns = [JoinColumn(name = "ROOM_ID")],
        inverseJoinColumns = [JoinColumn(name = "USER_ID")]
    )
    val users: MutableSet<UserEntity> = mutableSetOf(),

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    var status: RoomStatus,

    @Column(name = "SLOTS")
    var slots: Int,

    @Column(name = "PASS")
    val pass: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "MATCH_ID")
    val matchs: MutableList<MatchEntity> = mutableListOf()
)