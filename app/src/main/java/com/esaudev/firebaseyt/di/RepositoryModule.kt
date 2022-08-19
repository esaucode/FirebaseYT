package com.esaudev.firebaseyt.di

import com.esaudev.firebaseyt.data.remote.FirebaseAuthRepositoryImpl
import com.esaudev.firebaseyt.data.remote.FirestoreUserRepositoryImpl
import com.esaudev.firebaseyt.domain.repository.AuthRepository
import com.esaudev.firebaseyt.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepository: FirebaseAuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(userRepository: FirestoreUserRepositoryImpl): UserRepository
}