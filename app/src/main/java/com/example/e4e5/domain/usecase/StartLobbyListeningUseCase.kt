package com.example.e4e5.domain.usecase

import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.domain.repository.LobbyRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class StartLobbyListeningUseCase @Inject constructor(
    private val repository: LobbyRepository
) {

    operator fun invoke(lobbyId: String) : StateFlow<Lobby?> {
        return repository.startLobbyListening(lobbyId)
    }
}