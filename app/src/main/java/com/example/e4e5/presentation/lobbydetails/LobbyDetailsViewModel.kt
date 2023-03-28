package com.example.e4e5.presentation.lobbydetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.e4e5.common.Resource
import com.example.e4e5.domain.model.LobbyColor
import com.example.e4e5.domain.usecase.ChangeReadyStateUseCase
import com.example.e4e5.domain.usecase.EndLobbyListeningUseCase
import com.example.e4e5.domain.usecase.SetColorInLobbyUseCase
import com.example.e4e5.domain.usecase.StartLobbyListeningUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LobbyDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    startLobbyListeningUseCase: StartLobbyListeningUseCase,
    private val endLobbyListeningUseCase: EndLobbyListeningUseCase,
    private val changeReadyStateUseCase: ChangeReadyStateUseCase,
    private val setColorInLobbyUseCase: SetColorInLobbyUseCase,
) : ViewModel() {

    private val lobbyId: String = checkNotNull(savedStateHandle["lobbyId"])

    val lobby = startLobbyListeningUseCase(lobbyId)

    var gameCreationState: StateFlow<Resource<String>?> = MutableStateFlow(null)

    // Set pieces color, only for host
    fun setColor(color: LobbyColor) {
        setColorInLobbyUseCase(color)
    }

    // Only for host and when both players are ready
    fun startGame() {
//        lobby.value?.let { l ->
//            if (l.hostReady && l.secondPlayerReady)
//        }
    }

    fun changeReadyState() {
        changeReadyStateUseCase()
    }
}