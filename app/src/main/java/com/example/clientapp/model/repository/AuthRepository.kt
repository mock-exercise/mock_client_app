package com.example.clientapp.model.repository

import com.example.clientapp.base.BaseRepository
import com.example.clientapp.model.localsource.DataStoreManager
import com.example.clientapp.model.remotesource.AuthService
import com.example.connectorlibrary.enitity.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val service: AuthService,
    private val dataStoreManager: DataStoreManager
    ): BaseRepository() {

    suspend fun loginUser(phoneNumber: String) = safeApiCall{
        service.loginUser(phoneNumber)
    }

    suspend fun registerUserAccount(user: User) = safeApiCall {
        service.registerUserAccount(user)
    }

    // handle data store
    suspend fun saveAuthToken(token: Int) = safeApiCall {
        dataStoreManager.saveAccessTokens(token)
    }

}