package com.gui.top.trumps.api.v1.request

data class MatchNewRoundRequest(val id: String, val cardsId: List<String>)