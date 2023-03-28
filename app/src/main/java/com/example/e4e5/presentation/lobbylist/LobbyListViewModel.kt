package com.example.e4e5.presentation.lobbylist

import androidx.lifecycle.ViewModel
import com.example.e4e5.common.Resource
import com.example.e4e5.domain.usecase.CreateLobbyUseCase
import com.example.e4e5.domain.usecase.EndLobbyListListeningUseCase
import com.example.e4e5.domain.usecase.StartLobbyListListeningUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LobbyListViewModel @Inject constructor(
    startLobbyListListeningUseCase: StartLobbyListListeningUseCase,
    private val endLobbyListListeningUseCase: EndLobbyListListeningUseCase,
    private val createLobbyUseCase: CreateLobbyUseCase
) : ViewModel() {

    val lobbyList = startLobbyListListeningUseCase()

    // Stores created lobby id or null if not created
    var lobbyCreationState: StateFlow<Resource<String>?> = MutableStateFlow(null)

    fun endLobbyListListening() {
        endLobbyListListeningUseCase()
    }

    fun createNewLobby() {
        if (lobbyCreationState.value == null || lobbyCreationState.value is Resource.Error) {
            lobbyCreationState = createLobbyUseCase()
        }
    }
}