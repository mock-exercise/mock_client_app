package com.example.clientapp.model.repository

import com.example.clientapp.base.BaseRepository
import com.example.clientapp.model.remotesource.HomeService
import com.example.connectorlibrary.enitity.Health
import com.example.connectorlibrary.enitity.User
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: HomeService
): BaseRepository() {

    // Basic information
    suspend fun getGender() = safeApiCall {
        service.getGender()
    }

    suspend fun getSymptom()= safeApiCall{
        service.getSymptom()
    }

    suspend fun getStatus()= safeApiCall{
        service.getStatus()
    }

    // Insert Dialog
    suspend fun insertHealth(health: Health) = safeApiCall {
        service.insertHealth(health)
    }

    // Chart Fragment
    suspend fun getHistoryCovidVN()= safeApiCall{
        service.getHistoryCovidVn()
    }

    suspend fun getHistoryCovidWorld()= safeApiCall{
        service.getHistoryCovidVn()
    }

    suspend fun getStatisticCovidVn()= safeApiCall{
        service.getStatisticCovidVn()
    }

    suspend fun getStatisticCovidWorld()= safeApiCall{
        service.getStatisticCovidWorld()
    }

    // History Fragment

    suspend fun getUserHealths()= safeApiCall{
        service.getUserHealths()
    }

    // User Fragment
    suspend fun getUser(userID: Int)= safeApiCall{
        service.getUser(userID)
    }

    suspend fun updateUser(user:User)= safeApiCall{
        service.updateUser(user)
    }
}