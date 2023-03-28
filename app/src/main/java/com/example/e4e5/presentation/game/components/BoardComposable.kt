package com.example.e4e5.presentation.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e4e5.domain.model.BoardTheme
import com.example.e4e5.presentation.game.UiGameState
import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square

@Composable
fun BoardComposable(
    theme: BoardTheme,
    board: Board,
    gameState: UiGameState,
    onSquareClick: (Square) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = MutableInteractionSource()
    LazyVerticalGrid(
        columns = GridCells.Fixed(8),
        userScrollEnabled = false,
        modifier = modifier
    ) {
        items(64) { i ->
            val squareIndex = if (gameState.playerSide == Side.WHITE)
                (7 - i / 8) * 8 + i % 8 else i
            val square = Square.squareAt(squareIndex)
            val piece = board.getPiece(square)

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(
                        color = if ((squareIndex + squareIndex / 8 % 2) % 2 == 0)
                            theme.blackSquareColor else theme.whiteSquareColor
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onSquareClick(Square.squareAt(squareIndex))
                    },
                contentAlignment = Alignment.Center
            ) {
                if (square in gameState.availableSquares && piece.fenSymbol == ".") Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(theme.availableTurnColor)
                )
                if (piece.fenSymbol != ".") {
                    val imageModifier = if (square in gameState.availableSquares)
                        Modifier.background(
                            theme.availableTurnColor,
                            CircleShape
                        ) else Modifier
                    Image(
                        painter = painterResource(
                            id = theme.getDrawableId(
                                piece.pieceType,
                                piece.pieceSide
                            )
                        ),
                        contentDescription = piece.pieceType.value(),
                        modifier = imageModifier.padding(2.dp)
                    )
                }

                // Square letter
                if (i in 56..63) {
                    Text(
                        text = square.name[0].toString(),
                        color = if ((squareIndex + squareIndex / 8 % 2) % 2 == 0)
                            theme.whiteSquareColor else theme.blackSquareColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight(800),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 2.dp)
                    )
                }

                // Square number
                if (i % 8 == 0) {
                    Text(
                        text = square.name[1].toString(),
                        color = if ((squareIndex + squareIndex / 8 % 2) % 2 == 0)
                            theme.whiteSquareColor else theme.blackSquareColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight(800),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 2.dp)
                    )
                }
            }
        }
    }
}
