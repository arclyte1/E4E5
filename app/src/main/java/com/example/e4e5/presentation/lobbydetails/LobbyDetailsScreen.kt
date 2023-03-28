package com.example.e4e5.presentation.lobbydetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.e4e5.domain.model.LobbyColor


// choose color (for host)
// get ready/unready
// start game (for host)
@Composable
fun LobbyDetailsScreen(
    viewModel: LobbyDetailsViewModel = hiltViewModel()
) {
    val lobby = viewModel.lobby.collectAsState()
    Column {
        Row {
            lobby.value?.let { l ->
                Player(
                    name = l.hostName,
                    avatarUrl = l.hostAvatarUrl,
                    isReady = l.hostReady
                )
                Player(
                    name = l.secondPlayerName,
                    avatarUrl = l.secondPlayerAvatarUrl,
                    isReady = l.secondPlayerReady
                )
            }
        }
        Row {
            Button(onClick = {
                viewModel.setColor(LobbyColor.WHITE)
            }) {
                Text(LobbyColor.WHITE.name)
            }
            Button(onClick = {
                viewModel.setColor(LobbyColor.RANDOM)
            }) {
                Text(LobbyColor.RANDOM.name)
            }
            Button(onClick = {
                viewModel.setColor(LobbyColor.BLACK)
            }) {
                Text(LobbyColor.BLACK.name)
            }
        }
        Button(onClick = {
            viewModel.changeReadyState()
        }) {
            Text("Ready")
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Player(
    name: String?,
    avatarUrl: String?,
    isReady: Boolean,
) {
    Column {
        avatarUrl?.let { url ->
            GlideImage(
                model = url,
                contentDescription = name.toString()
            )
        }
        Text(name.toString())
        Text(isReady.toString())
    }
}