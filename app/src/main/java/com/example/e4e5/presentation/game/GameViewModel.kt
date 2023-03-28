package com.example.e4e5.presentation.game

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.bhlangonijr.chesslib.*
import com.github.bhlangonijr.chesslib.move.Move
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private val _board = mutableStateOf(Board())
    val board: State<Board> = _board

    // list of all possible moves for current position
    private var legalMoves = _board.value.legalMoves()

    // board state for ui
    private val _uiGameState = mutableStateOf(UiGameState(playerSide = Side.BLACK))
    val uiGameState: State<UiGameState> = _uiGameState

    private var promotingSquare: Square? = null

    fun onSquareClick(square: Square) {

        // clicked on same square when making turn
        if (square == _uiGameState.value.makingTurnSquare) {
            // TODO
            _uiGameState.value = _uiGameState.value.copy(
                makingTurnSquare = Square.NONE,
                availableSquares = emptyList(),
                isPromotion = false,
                ifGameFinished = false,
            )
            return
        }

        // clicked on player's piece -> showing possible squares to move
        if (_board.value.getPiece(square).pieceSide == _board.value.sideToMove) {
            val availableSquaresList = mutableListOf<Square>()
            for (move in legalMoves)
                if (move.from == square)
                    availableSquaresList.add(move.to)
            if (availableSquaresList.isNotEmpty()) {
                _uiGameState.value = _uiGameState.value.copy(
                    makingTurnSquare = square,
                    availableSquares = availableSquaresList.toList()
                )
            } else {
                _uiGameState.value = _uiGameState.value.copy(
                    makingTurnSquare = Square.NONE,
                    availableSquares = emptyList(),
                    isPromotion = false,
                )
            }
        } else { // clicked on empty square or on opponent's piece -> checking if can make turn there
            if (square in _uiGameState.value.availableSquares) { // can make move
                val makingTurnPiece = _board.value.getPiece(_uiGameState.value.makingTurnSquare)
                if (
                    _uiGameState.value.playerSide == Side.WHITE && makingTurnPiece.fenSymbol == "P"
                    && square.rank == Rank.RANK_8 || _uiGameState.value.playerSide == Side.BLACK
                    && makingTurnPiece.fenSymbol == "p" && square.rank == Rank.RANK_1
                ) { // promotion move
                    promotingSquare = square
                    _uiGameState.value = _uiGameState.value.copy(
                        isPromotion = true
                    )
                } else { // regular move
                    val move = Move(_uiGameState.value.makingTurnSquare, square)
                    Log.d("MOVE", move.toString())
                    _board.value.doMove(move)
                    Log.d("HISTORY", board.value.history.toString())
                    legalMoves = _board.value.legalMoves()
                    _uiGameState.value = _uiGameState.value.copy(
                        makingTurnSquare = Square.NONE,
                        availableSquares = emptyList()
                    )
                }
            } else { // cant make move there
                _uiGameState.value = _uiGameState.value.copy(
                    makingTurnSquare = Square.NONE,
                    availableSquares = emptyList()
                )
            }
        }
    }

    fun onPromotionSelected(pieceType: PieceType) {
        if (_uiGameState.value.isPromotion) {
            val move = Move(
                _uiGameState.value.makingTurnSquare,
                promotingSquare,
                Piece.make(_uiGameState.value.playerSide, pieceType)
            )
            Log.d("MOVE", move.toString())
            _board.value.doMove(
                move
            )
            Log.d("HISTORY", board.value.history.toString())
            legalMoves = _board.value.legalMoves()
            promotingSquare = null
            _uiGameState.value = _uiGameState.value.copy(
                makingTurnSquare = Square.NONE,
                availableSquares = emptyList(),
                isPromotion = false
            )
        }
    }

    fun undoPromotion() {
        if (_uiGameState.value.isPromotion) {
            _uiGameState.value = _uiGameState.value.copy(
                makingTurnSquare = Square.NONE,
                availableSquares = emptyList(),
                isPromotion = false
            )
        }
    }
}
