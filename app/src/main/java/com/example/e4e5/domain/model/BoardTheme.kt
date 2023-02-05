package com.example.e4e5.domain.model

import androidx.compose.ui.graphics.Color
import com.github.bhlangonijr.chesslib.PieceType
import com.github.bhlangonijr.chesslib.Side

data class BoardTheme(
    val whitePieces: PiecesTheme,
    val blackPieces: PiecesTheme,
    val whiteSquareColor: Color,
    val blackSquareColor: Color,
    val availableTurnColor: Color,
) {

    fun getDrawableId(pieceType: PieceType, side: Side): Int {
        return if (side == Side.WHITE) {
            whitePieces.getDrawableId(pieceType)
        } else {
            blackPieces.getDrawableId(pieceType)
        }
    }
}
