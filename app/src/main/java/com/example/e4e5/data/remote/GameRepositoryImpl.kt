package com.example.e4e5.data.remote

import com.example.e4e5.common.Resource
import com.example.e4e5.data.remote.dto.GameDto
import com.example.e4e5.domain.model.Game
import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.domain.repository.GameRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameRepositoryImpl : GameRepository {

    private val game = MutableStateFlow<Game?>(null)

    private val gameListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            try {
                game.value = snapshot.getValue(GameDto::class.java)!!.toGame(
                    gameId = snapshot.key!!,
                    uid = Firebase.auth.uid!!
                )
            } catch (e: Exception) {
                game.value = null
                e.printStackTrace()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            game.value = null
        }
    }

    override fun createGame(lobby: Lobby): StateFlow<Resource<String>> {
        val gameCreationState = MutableStateFlow<Resource<String>>(Resource.Loading())
        if (Firebase.auth.currentUser == null) {
            gameCreationState.value = Resource.Error("User is not signed in")
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val key = Firebase.database.getReference("games").push().key
                if (key != null) {
                    Firebase.database.getReference("games/$key").setValue(
                        GameDto.makeNewGame(lobby)
                    ).addOnSuccessListener {
                        setCurrentGame(key, lobby.host, lobby.secondPlayer!!)
                    }.addOnCanceledListener {

                    }
                }
            }
        }
        return gameCreationState.asStateFlow()
    }

    private fun setCurrentGame(key: String, host: String, secondPlayer: String) {
        Firebase.database.getReference("users/$host/current_game").setValue(key)
        Firebase.database.getReference("users/$secondPlayer/current_game").setValue(key)
    }

    override fun startGameListening(gameId: String): StateFlow<Game?> {
        Firebase.database.getReference("games/$gameId").addValueEventListener(gameListener)
        return game.asStateFlow()
    }

    override fun endGameListening() {
        game.value?.let { g ->
            Firebase.database.getReference("games/${g.id}").removeEventListener(gameListener)
        }
    }
}