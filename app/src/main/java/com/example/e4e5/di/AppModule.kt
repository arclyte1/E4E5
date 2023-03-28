package com.example.e4e5.di

import com.example.e4e5.data.remote.GameRepositoryImpl
import com.example.e4e5.data.remote.LobbyRepositoryImpl
import com.example.e4e5.domain.repository.GameRepository
import com.example.e4e5.domain.repository.LobbyRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideLobbyRepository(): LobbyRepository {
        return LobbyRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGameRepository(): GameRepository {
        return GameRepositoryImpl()
    }
}