package com.example.e4e5.data.remote.dto

import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.domain.model.LobbyColor

data class LobbyDto(
    val title: String? = null,
    val host: String? = null,
    val second_player: String? = null,
    val host_color: String? = null,
    val host_ready: Boolean? = null,
    val second_player_ready: Boolean? = null,
    val host_avatar_url: String? = null,
    val second_player_avatar_url: String? = null,
    val host_name: String? = null,
    val second_player_name: String? = null,
) {

    fun toLobby(lobbyId: String, uid: String): Lobby? {
        return if (title != null && host != null && host_ready != null && host_name != null
            && host_color != null && second_player_ready != null) {
            Lobby(
                id = lobbyId,
                title = title,
                host = host,
                secondPlayer = second_player,
                hostName = host_name,
                secondPlayerName = second_player_name,
                hostAvatarUrl = host_avatar_url,
                secondPlayerAvatarUrl = second_player_avatar_url,
                hostReady = host_ready,
                secondPlayerReady = second_player_ready,
                color = when {
                    uid == host && host_color == LobbyColor.WHITE.name || uid == second_player
                            && host_color == LobbyColor.BLACK.name -> LobbyColor.WHITE

                    uid == host && host_color == LobbyColor.BLACK.name || uid == second_player
                            && host_color == LobbyColor.WHITE.name -> LobbyColor.BLACK

                    else -> LobbyColor.RANDOM
                }
            )
        } else {
            null
        }
    }

    companion object {
        fun makeNewLobby(
            uid: String,
            title: String,
            playerName: String,
            avatarUrl: String,
        ): LobbyDto {
            return LobbyDto(
                title = title,
                host = uid,
                second_player = null,
                host_color = LobbyColor.RANDOM.name,
                host_ready = false,
                second_player_ready = false,
                host_avatar_url = avatarUrl,
                second_player_avatar_url = null,
                host_name = playerName,
                second_player_name = null,
            )
        }
    }
}
