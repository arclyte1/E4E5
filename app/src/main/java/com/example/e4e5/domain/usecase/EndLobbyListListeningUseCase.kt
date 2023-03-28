package com.example.e4e5.domain.usecase

import com.example.e4e5.domain.repository.LobbyRepository
import javax.inject.Inject

class EndLobbyListListeningUseCase @Inject constructor(
    private val lobbyRepository: LobbyRepository
) {

    operator fun invoke() {
        lobbyRepository.endLobbyListListening()
    }
}