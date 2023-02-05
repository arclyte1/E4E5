package com.example.e4e5.presentation

sealed class Screen(val route: String) {
    object SignInScreen: Screen("sign_in_screen")
    object LobbyListScreen: Screen("lobby_list_screen")
    object LobbyDetailsScreen: Screen("lobby_details_screen")
    object GameScreen: Screen("game_screen")
}
