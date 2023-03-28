package com.example.e4e5.data.remote

import android.util.Log
import com.example.e4e5.data.remote.dto.GameDto
import com.example.e4e5.domain.model.Game
import com.example.e4e5.domain.repository.FirebaseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FirebaseRepositoryImpl : FirebaseRepository {

    private val gameFlow = MutableStateFlow<Game?>(null)

    private val gameStateListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (Firebase.auth.currentUser != null) {
                try {
                    gameFlow.value = snapshot.getValue(GameDto::class.java)?.toGame(
                        snapshot.key!!,
                        Firebase.auth.uid!!
                    )
                } catch (e: Exception) {
                    Log.e(TAG, e.localizedMessage ?: "data parse exception")
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e(TAG, error.message)
        }
    }
    override fun getGameStateFlow(): StateFlow<Game?> {
        return gameFlow
    }

    companion object {
        const val TAG = "FirebaseRepository"
    }
}