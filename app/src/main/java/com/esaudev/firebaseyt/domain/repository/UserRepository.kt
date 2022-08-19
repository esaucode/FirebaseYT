package com.esaudev.firebaseyt.domain.repository

import com.esaudev.firebaseyt.domain.model.User

interface UserRepository {

    suspend fun createUser(user: User): Boolean

    suspend fun getUser(uid: String): User
}