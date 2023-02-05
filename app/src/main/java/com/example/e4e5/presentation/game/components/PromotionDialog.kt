package com.example.e4e5.presentation.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.e4e5.domain.model.BoardTheme
import com.github.bhlangonijr.chesslib.PieceType
import com.github.bhlangonijr.chesslib.Side

@Composable
fun PromotionDialog(
    theme: BoardTheme,
    playerSide: Side,
    promotionSelect: (PieceType) -> Unit,
    undoPromotion: () -> Unit
) {
    val piecesTheme = if (playerSide == Side.WHITE)
        theme.whitePieces else theme.blackPieces
    AlertDialog(
        onDismissRequest = {
            undoPromotion()
        },
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row {
                    Image(
                        painter = painterResource(id = piecesTheme.queen),
                        contentDescription = piecesTheme.queen.toString(),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(1f)
                            .background(theme.whiteSquareColor)
                            .clickable {
                                promotionSelect(PieceType.QUEEN)
                            }
                    )
                    Image(
                        painter = painterResource(id = piecesTheme.rook),
                        contentDescription = piecesTheme.rook.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(theme.blackSquareColor)
                            .clickable {
                                promotionSelect(PieceType.ROOK)
                            }
                    )
                }
                Row {
                    Image(
                        painter = painterResource(id = piecesTheme.knight),
                        contentDescription = piecesTheme.knight.toString(),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(1f)
                            .background(theme.blackSquareColor)
                            .clickable {
                                promotionSelect(PieceType.KNIGHT)
                            }
                    )
                    Image(
                        painter = painterResource(id = piecesTheme.bishop),
                        contentDescription = piecesTheme.bishop.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(theme.whiteSquareColor)
                            .clickable {
                                promotionSelect(PieceType.BISHOP)
                            }
                    )
                }
            }
        }
    )
}
