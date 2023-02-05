package com.example.e4e5.presentation.lobbylist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LobbyListViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _username = mutableStateOf(auth.currentUser?.displayName.toString())
    val username: State<String> = _username

}