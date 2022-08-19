package com.esaudev.firebaseyt.di

import com.esaudev.firebaseyt.data.remote.FirebaseAuthRepositoryImpl
import com.esaudev.firebaseyt.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepository: FirebaseAuthRepositoryImpl): AuthRepository
}