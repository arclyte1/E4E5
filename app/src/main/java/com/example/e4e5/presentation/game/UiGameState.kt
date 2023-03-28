package com.example.e4e5.presentation.game

import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.game.GameResult

data class UiGameState(
    val playerSide: Side,
    val makingTurnSquare: Square = Square.NONE,
    val availableSquares: List<Square> = emptyList(),
    val isPromotion: Boolean = false,
    val ifGameFinished: Boolean = false,
    val gameResult: GameResult = GameResult.ONGOING,
)
