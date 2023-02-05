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
    private val _gameState = mutableStateOf(GameState(playerSide = Side.BLACK))
    val gameState: State<GameState> = _gameState

    private var promotingSquare: Square? = null

    fun onSquareClick(square: Square) {

        // clicked on same square when making turn
        if (square == _gameState.value.makingTurnSquare) {
            // TODO
            _gameState.value = _gameState.value.copy(
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
                _gameState.value = _gameState.value.copy(
                    makingTurnSquare = square,
                    availableSquares = availableSquaresList.toList()
                )
            } else {
                _gameState.value = _gameState.value.copy(
                    makingTurnSquare = Square.NONE,
                    availableSquares = emptyList(),
                    isPromotion = false,
                )
            }
        } else { // clicked on empty square or on opponent's piece -> checking if can make turn there
            if (square in _gameState.value.availableSquares) { // can make move
                val makingTurnPiece = _board.value.getPiece(_gameState.value.makingTurnSquare)
                if (
                    _gameState.value.playerSide == Side.WHITE && makingTurnPiece.fenSymbol == "P"
                    && square.rank == Rank.RANK_8 || _gameState.value.playerSide == Side.BLACK
                    && makingTurnPiece.fenSymbol == "p" && square.rank == Rank.RANK_1
                ) { // promotion move
                    promotingSquare = square
                    _gameState.value = _gameState.value.copy(
                        isPromotion = true
                    )
                } else { // regular move
                    val move = Move(_gameState.value.makingTurnSquare, square)
                    Log.d("MOVE", move.toString())
                    _board.value.doMove(move)
                    Log.d("HISTORY", board.value.history.toString())
                    legalMoves = _board.value.legalMoves()
                    _gameState.value = _gameState.value.copy(
                        makingTurnSquare = Square.NONE,
                        availableSquares = emptyList()
                    )
                }
            } else { // cant make move there
                _gameState.value = _gameState.value.copy(
                    makingTurnSquare = Square.NONE,
                    availableSquares = emptyList()
                )
            }
        }
    }

    fun onPromotionSelected(pieceType: PieceType) {
        if (_gameState.value.isPromotion) {
            val move = Move(
                _gameState.value.makingTurnSquare,
                promotingSquare,
                Piece.make(_gameState.value.playerSide, pieceType)
            )
            Log.d("MOVE", move.toString())
            _board.value.doMove(
                move
            )
            Log.d("HISTORY", board.value.history.toString())
            legalMoves = _board.value.legalMoves()
            promotingSquare = null
            _gameState.value = _gameState.value.copy(
                makingTurnSquare = Square.NONE,
                availableSquares = emptyList(),
                isPromotion = false
            )
        }
    }

    fun undoPromotion() {
        if (_gameState.value.isPromotion) {
            _gameState.value = _gameState.value.copy(
                makingTurnSquare = Square.NONE,
                availableSquares = emptyList(),
                isPromotion = false
            )
        }
    }
}
