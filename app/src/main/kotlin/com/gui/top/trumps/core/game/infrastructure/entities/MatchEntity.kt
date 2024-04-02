package com.gui.top.trumps.core.game.infrastructure.entities

import com.gui.top.trumps.core.game.domain.MatchStatus
import jakarta.persistence.*

@Entity
@Table(name = "MATCHES")
data class MatchEntity(

    @Id
    @Column(name = "ID")
    val id: String = "",

    @Column(name = "DECK_ID")
    val deckId: String,

    @Column(name = "ROUND")
    val round: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    var status: MatchStatus,

    @Column(name = "ROOM_ID")
    val roomId: String,

    @Column(name = "WINNER")
    var winner: String? = null,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinTable(
        name = "MATCH_PLAYERS",
        joinColumns = [JoinColumn(name = "MATCH_ID")],
        inverseJoinColumns = [JoinColumn(name = "PLAYER_ID")]
    )
    val players: Set<PlayerEntity>
)