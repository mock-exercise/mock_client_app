package com.example.clientapp.data.repository.remotesource

import com.example.connectorlibrary.controller.ServiceControllerUser
import com.example.connectorlibrary.enitity.Health
import com.example.connectorlibrary.enitity.User
import javax.inject.Inject

class HomeService @Inject constructor(private val service: ServiceControllerUser) {

    // Basic information
    fun getGender(){
        service.getGender()
    }

    fun getSymptom(){
        service.getSymptom()
    }

    fun getStatus(){
        service.getStatus()
    }

    // Insert Dialog
    fun insertHealth(health: Health){
        service.insertHealth(health)
    }

    // Chart Fragment
    fun getHistoryCovidVn(){
        service.getHistoryCovidVn()
    }

    fun getHistoryCovidWorld(){
        service.getHistoryCovidWorld()
    }

    // History Fragment

    fun getUserHealths(){
        service.getUserHealths()
    }

    // User Fragment
    fun getUser(userID: Int){
        service.getUserInformation(userID)
    }

    fun updateUser(user: User){
        service.updateUser(user)
    }
}