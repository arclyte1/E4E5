package com.example.e4e5.domain.usecase

import com.example.e4e5.domain.model.LobbyColor
import com.example.e4e5.domain.repository.LobbyRepository
import javax.inject.Inject

class SetColorInLobbyUseCase @Inject constructor(
    private val repository: LobbyRepository
) {

    operator fun invoke(color: LobbyColor) {
        repository.setColor(color)
    }
}