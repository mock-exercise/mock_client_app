package com.example.clientapp.model.remotesource

import com.example.connectorlibrary.controller.ServiceControllerUser
import com.example.connectorlibrary.enitity.Health
import javax.inject.Inject

class HomeService @Inject constructor(private val service: ServiceControllerUser) {

    // Basic information
    fun getGender(){
        service.getGender()
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