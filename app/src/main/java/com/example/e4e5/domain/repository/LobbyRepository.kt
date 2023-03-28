package com.example.e4e5.domain.repository

import com.example.e4e5.common.Resource
import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.domain.model.LobbyColor
import kotlinx.coroutines.flow.StateFlow

interface LobbyRepository {

    fun startLobbyListListening(): StateFlow<List<Lobby>>

    fun endLobbyListListening()

    fun startLobbyListening(lobbyId: String) : StateFlow<Lobby?>

    fun endLobbyListening()

    fun createLobby(): StateFlow<Resource<String>>

    fun changeReadyState()

    fun setColor(color: LobbyColor)
}