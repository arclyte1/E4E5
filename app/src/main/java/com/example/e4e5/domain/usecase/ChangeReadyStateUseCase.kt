package com.example.e4e5.domain.usecase

import com.example.e4e5.domain.repository.LobbyRepository
import javax.inject.Inject

class ChangeReadyStateUseCase @Inject constructor(
    private val repository: LobbyRepository
) {

    operator fun invoke() {
        repository.changeReadyState()
    }
}