package com.example.e4e5.domain.model

enum class LobbyColor {

    BLACK, WHITE, RANDOM;

    operator fun not(): LobbyColor {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
            RANDOM -> RANDOM
        }
    }
}