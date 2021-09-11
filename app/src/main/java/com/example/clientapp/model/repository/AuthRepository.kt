package com.example.clientapp.model.repository

import com.example.clientapp.base.BaseRepository
import com.example.clientapp.model.remotesource.AuthService
import com.example.connectorlibrary.enitity.User
import java.security.CodeSource
import javax.inject.Inject

class AuthRepository @Inject constructor(private val service: AuthService): BaseRepository() {

    suspend fun loginUser(phoneNumber: String) = safeApiCall{
        service.loginUser(phoneNumber)
    }

    suspend fun registerUserAccount(user: User) = safeApiCall {
        service.registerUserAccount(user)
    }
}