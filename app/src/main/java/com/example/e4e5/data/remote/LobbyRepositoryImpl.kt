package com.example.e4e5.data.remote

import android.util.Log
import com.example.e4e5.common.Resource
import com.example.e4e5.data.remote.dto.LobbyDto
import com.example.e4e5.domain.model.Lobby
import com.example.e4e5.domain.model.LobbyColor
import com.example.e4e5.domain.repository.LobbyRepository
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

class LobbyRepositoryImpl : LobbyRepository {

    private val lobby = MutableStateFlow<Lobby?>(null)

    private val lobbyListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            try {
                lobby.value = snapshot.getValue(LobbyDto::class.java)!!.toLobby(
                    snapshot.key!!,
                    Firebase.auth.uid!!,
                )
            } catch (e: Exception) {
                lobby.value = null
                e.printStackTrace()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            lobby.value = null
        }
    }

    private val lobbyList = MutableStateFlow<List<Lobby>>(emptyList())

    private val lobbyListListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            try {
                val mLobbyList = mutableListOf<Lobby>()
                snapshot.children.forEach { dataSnapshot ->
                    Log.d(TAG, dataSnapshot.value.toString())
                    dataSnapshot.getValue(LobbyDto::class.java)!!.toLobby(
                        snapshot.key!!,
                        Firebase.auth.uid!!
                    )?.let { mLobbyList.add(it) }
                }
                lobbyList.value = mLobbyList.toList()
            } catch (e: Exception) {
                lobbyList.value = emptyList()
                Log.e(TAG, e.localizedMessage ?: "get lobby list exception")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            lobbyList.value = emptyList()
        }
    }

    override fun startLobbyListListening(): StateFlow<List<Lobby>> {
        Firebase.database.reference.child("lobbies").addValueEventListener(
            lobbyListListener
        )
        return lobbyList.asStateFlow()
    }

    override fun endLobbyListListening() {
        Firebase.database.reference.child("lobbies").removeEventListener(
            lobbyListListener
        )
    }

    override fun startLobbyListening(lobbyId: String): StateFlow<Lobby?> {
        if (lobby.value != null)
            endLobbyListening()
        Firebase.database.reference.child("lobbies").child(lobbyId).addValueEventListener(
            lobbyListener
        )
        return lobby.asStateFlow()
    }

    override fun endLobbyListening() {
        lobby.value?.let { l ->
            Firebase.database.reference.child("lobbies").child(l.id).addValueEventListener(
                lobbyListener
            )
        }
    }

    override fun createLobby(): StateFlow<Resource<String>> {
        val completionFlow = MutableStateFlow<Resource<String>>(Resource.Loading())
        if (Firebase.auth.currentUser == null) {
            completionFlow.value = Resource.Error("User is not signed in")
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val key = Firebase.database.reference.child("lobbies").push().key
                if (key != null) {
                    val isCurrentLobbySet = setCurrentLobby(key)
                    if (!isCurrentLobbySet) {
                        completionFlow.value = Resource.Error("Can't set lobby")
                        return@launch
                    }

                    Firebase.database.reference.child("lobbies").child(key).setValue(
                        LobbyDto.makeNewLobby(
                            Firebase.auth.uid!!,
                            "${Firebase.auth.currentUser!!.displayName}'s lobby",
                            Firebase.auth.currentUser!!.displayName!!,
                            Firebase.auth.currentUser!!.photoUrl!!.toString()
                        )
                    )
                    completionFlow.value = Resource.Success(key)
                } else {
                    completionFlow.value = Resource.Error("Can't create lobby")
                }
            }
        }
        return completionFlow.asStateFlow()
    }

    override fun changeReadyState() {
        lobby.value?.let { l ->
            val lobbyReference = Firebase.database.reference.child("lobbies").child(l.id)
            when (Firebase.auth.uid) {
                l.host -> {
                    lobbyReference.child("host_ready").setValue(!l.hostReady)
                }
                l.secondPlayer -> {
                    lobbyReference.child("second_player_ready").setValue(!l.hostReady)
                }
                else -> {}
            }
        }
    }

    override fun setColor(color: LobbyColor) {
        lobby.value?.let { l ->
            if (Firebase.auth.uid == l.host) {
                Firebase.database.getReference("lobbies/${l.id}/host_color").setValue(color.name)
            }
        }
    }

    private fun setCurrentLobby(lobbyId: String) : Boolean {
        return if (Firebase.auth.currentUser != null) {
            Firebase.database.reference
                .child("users")
                .child(Firebase.auth.uid!!)
                .child("current_lobby")
                .setValue(lobbyId)
            true
        } else
            false
    }

    companion object {
        const val TAG = "LobbyRepository"
    }
}