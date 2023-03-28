package com.example.e4e5.domain.model


data class Lobby(
    val id: String,
    val title: String,
    val host: String,
    val secondPlayer: String?,
    val hostName: String,
    val secondPlayerName: String?,
    val hostAvatarUrl: String?,
    val secondPlayerAvatarUrl: String?,
    val hostReady: Boolean,
    val secondPlayerReady: Boolean,
    val color: LobbyColor,
)
