package com.example.e4e5.domain.repository

import com.example.e4e5.common.Resource
import com.example.e4e5.domain.model.Game
import com.example.e4e5.domain.model.Lobby
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {

    fun createGame(lobby: Lobby): StateFlow<Resource<String>>

    fun startGameListening(gameId: String): StateFlow<Game?>

    fun endGameListening()
}