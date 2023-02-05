package com.example.e4e5.domain.model

import com.github.bhlangonijr.chesslib.PieceType

data class PiecesTheme(
    val pawn: Int,
    val knight: Int,
    val bishop: Int,
    val rook: Int,
    val king: Int,
    val queen: Int,
) {

    fun getDrawableId(pieceType: PieceType): Int {
        return when (pieceType) {
            PieceType.PAWN -> pawn
            PieceType.QUEEN -> queen
            PieceType.ROOK -> rook
            PieceType.KNIGHT -> knight
            PieceType.BISHOP -> bishop
            PieceType.KING -> king
            PieceType.NONE -> 0
        }
    }
}
