package com.example.clientapp.model.repository

import com.example.clientapp.base.BaseRepository
import com.example.clientapp.model.remotesource.HomeService
import com.example.connectorlibrary.enitity.Health
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: HomeService
): BaseRepository() {

    // Basic information
    suspend fun getGender() = safeApiCall {
        service.getGender()
    }

    // Insert Dialog
    suspend fun insertHealth(health: Health) = safeApiCall {
        service.insertHealth(health)
    }

    // Chart Fragment
    suspend fun getHistoryCovidVN(){
        service.getHistoryCovidVn()
    }

    suspend fun getHistoryCovidWorld(){
        service.getHistoryCovidVn()
    }

    fun getStatisticCovidVn(){
        service.getStatisticCovidVn()
    }

    fun getStatisticCovidWorld(){
        service.getStatisticCovidWorld()
    }

    // History Fragment
    fun getSymptom(){
        service.getSymptom()
    }

    fun getUserHealths(){
        service.getUserHealths()
    }

    // User Fragment
    fun getUser(userID: Int){
        service.getUser(userID)
    }
}