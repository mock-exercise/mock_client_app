package com.example.clientapp.model.remotesource

import com.example.connectorlibrary.controller.ServiceControllerUser
import com.example.connectorlibrary.enitity.User
import javax.inject.Inject

class AuthService @Inject constructor(private val service: ServiceControllerUser) {

    fun loginUser(phoneNumber: String){
        service.userSignIn(phoneNumber)
    }

    fun registerUserAccount(user: User){
        service.userSignUp(user)
    }

    fun getGender(){
        service.getGender()
    }

    fun getActive(){
        service.getActive()
    }
}