package com.example.e4e5.domain.repository

import com.example.e4e5.domain.model.Game
import kotlinx.coroutines.flow.StateFlow

interface FirebaseRepository {
    fun getGameStateFlow(): StateFlow<Game?>
}