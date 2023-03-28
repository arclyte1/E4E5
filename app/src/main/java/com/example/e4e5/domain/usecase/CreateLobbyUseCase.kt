package com.example.e4e5.domain.usecase

import com.example.e4e5.common.Resource
import com.example.e4e5.domain.repository.LobbyRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Creates new lobby in remote db
 * Lobby host = current user
 * Lobby name = "Current User's lobby"
 */
class CreateLobbyUseCase @Inject constructor(
    private val lobbyRepository: LobbyRepository
) {

    operator fun invoke(): StateFlow<Resource<String>> {
        return lobbyRepository.createLobby()
    }
}