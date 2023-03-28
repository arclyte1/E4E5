package com.example.e4e5.domain.usecase

import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.domain.repository.LobbyRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class StartLobbyListListeningUseCase @Inject constructor(
    private val lobbyRepository: LobbyRepository
) {

    operator fun invoke(): StateFlow<List<Lobby>> {
        return lobbyRepository.startLobbyListListening()
    }
}