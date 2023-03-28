package com.example.e4e5.data.remote.dto

import com.example.e4e5.domain.model.Game
import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.domain.model.LobbyColor
import com.github.bhlangonijr.chesslib.Side
import kotlin.random.Random

data class GameDto(
    val colors: Map<String, String>,
    val fen: String,
    val host: String,
    val second_player: String,
    val making_turn: String,
    val moves: Map<Int, String>,
) {

    fun toGame(gameId: String, uid: String) = Game(
        id = gameId,
        playerSide = Side.fromValue(colors[uid]),
        fen = fen,
        isHost = uid == host,
        makingTurnSide = Side.fromValue(colors[making_turn]),
        moves = moves.values.toList()
    )

    companion object {
        fun makeNewGame(lobby: Lobby): GameDto {
            var hostColor = lobby.color
            if (hostColor == LobbyColor.RANDOM) {
                hostColor = if (Random.nextBoolean())
                    LobbyColor.WHITE
                else
                    LobbyColor.BLACK
            }
            return GameDto(
                colors = mapOf(
                    lobby.host to hostColor.name,
                    lobby.secondPlayer!! to (!hostColor).name
                ),
                fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
                host = lobby.host,
                second_player = lobby.secondPlayer,
                making_turn = if (hostColor == LobbyColor.WHITE) lobby.host else lobby.secondPlayer,
                moves = emptyMap(),
            )
        }
    }
}