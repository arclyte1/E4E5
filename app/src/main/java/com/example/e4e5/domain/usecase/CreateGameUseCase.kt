package com.example.e4e5.domain.usecase

import com.example.e4e5.common.Resource
import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.domain.repository.GameRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CreateGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    operator fun invoke(lobby: Lobby): StateFlow<Resource<String>> {
        return gameRepository.createGame(lobby)
    }
}