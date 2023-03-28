package com.example.e4e5.presentation.lobbylist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e4e5.common.Resource
import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.presentation.Screen
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LobbyListScreen(
    navController: NavController,
    signOut: () -> Unit,
    viewModel: LobbyListViewModel = hiltViewModel()
) {
    val lobbyCreationState = viewModel.lobbyCreationState.collectAsState()
    Log.d("LobbyListScreen", lobbyCreationState.toString())
    if (lobbyCreationState.value is Resource.Success) {
        viewModel.endLobbyListListening()
        viewModel.lobbyCreationState = MutableStateFlow(null)
        navController.navigate(
            Screen.LobbyDetailsScreen.route + "/${lobbyCreationState.value?.data}"
        )
    }
    val lobbyList = viewModel.lobbyList.collectAsState()
    Log.d("LobbyListScreen", lobbyList.value.size.toString())
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(lobbyList.value) {lobby: Lobby ->
                Text(lobby.title)
            }
        }
        FloatingActionButton(
            onClick = {
                viewModel.createNewLobby()
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
//    Column {
//        Text(viewModel.username.value)
//        Button(onClick = {
//            signOut()
//            navController.navigate(Screen.SignInScreen.route)
//        }) {
//            Text("Sign out")
//        }
//    }
}