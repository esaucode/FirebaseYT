package com.esaudev.firebaseyt.domain.repository

import com.esaudev.firebaseyt.util.Resource

interface AuthRepository {

    suspend fun login(email: String, password:String): Boolean

    suspend fun signUp(email:String, password: String): Boolean

}