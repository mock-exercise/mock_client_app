package com.example.clientapp.data.repository

import com.example.clientapp.base.BaseRepository
import com.example.clientapp.data.repository.localsource.DataStoreManager
import com.example.clientapp.data.repository.remotesource.AuthService
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

    suspend fun getGender() = safeApiCall {
        service.getGender()
    }

    // handle data store
    suspend fun saveAuthToken(token: Int) = safeApiCall {
        dataStoreManager.saveAccessTokens(token)
    }
}