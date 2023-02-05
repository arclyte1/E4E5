package com.example.e4e5.presentation.lobbylist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e4e5.presentation.Screen

@Composable
fun LobbyListScreen(
    navController: NavController,
    signOut: () -> Unit,
    viewModel: LobbyListViewModel = hiltViewModel()
) {
    Column {
        Text(viewModel.username.value)
        Button(onClick = {
            signOut()
            navController.navigate(Screen.SignInScreen.route)
        }) {
            Text("Sign out")
        }
    }
}