package com.example.e4e5.domain

import androidx.compose.ui.graphics.Color
import com.example.e4e5.R
import com.example.e4e5.domain.model.BoardTheme
import com.example.e4e5.domain.model.PiecesTheme
import kotlinx.coroutines.flow.MutableStateFlow

object BoardThemeState {
    val a = arrayOf(1)
    val b = arrayOf(2)
    val c = listOf(a, b)
    val theme = MutableStateFlow(
        BoardTheme(
            whitePieces = PiecesTheme(
                pawn = R.drawable.chess_plt45,
                knight = R.drawable.chess_nlt45,
                bishop = R.drawable.chess_blt45,
                rook = R.drawable.chess_rlt45,
                king = R.drawable.chess_klt45,
                queen = R.drawable.chess_qlt45,
            ),
            blackPieces = PiecesTheme(
                pawn = R.drawable.chess_pdt45,
                knight = R.drawable.chess_ndt45,
                bishop = R.drawable.chess_bdt45,
                rook = R.drawable.chess_rdt45,
                king = R.drawable.chess_kdt45,
                queen = R.drawable.chess_qdt45,
            ),
            whiteSquareColor = Color(0xffeeeeee),
            blackSquareColor = Color(0xff567696),
            availableTurnColor = Color(0x1c000000),
        )
    )
}