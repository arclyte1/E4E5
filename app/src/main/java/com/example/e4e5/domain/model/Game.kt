package com.example.e4e5.domain.model

import com.github.bhlangonijr.chesslib.Side


data class Game(
    val id: String,
    val playerSide: Side,
    val fen: String,
    val isHost: Boolean,
    val makingTurnSide: Side,
    val moves: List<String>,
)
