package com.example.clientapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.clientapp.model.repository.AuthRepository
import com.example.connectorlibrary.callback.CallbackConnector
import com.example.connectorlibrary.controller.ServiceControllerUser
import com.example.connectorlibrary.enitity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val service: ServiceControllerUser,
    private val repository: AuthRepository
    ): AndroidViewModel(application), LifecycleObserver,  CallbackConnector.CallbackConnectorUser {

    companion object{
        val TAG : String = AuthViewModel::class.java.simpleName
    }

    // Server Request
    fun registerAccount(userUser: User) = viewModelScope.launch{
        Log.e(TAG, "onUserSignUp: dã gửi", )
        repository.registerUserAccount(userUser)
    }

    fun loginAccount(phoneNumber: String) = viewModelScope.launch {
        repository.loginUser(phoneNumber)
    }

    // Server Response
    override fun onFailureResponse(failureResponse: FailureResponse) {

    }

    override fun onGetActive(activeResponse: ActiveResponse) {
    }

    override fun onGetGender(genderResponse: GenderResponse) {
    }

    override fun onGetStatisticCovid(statisticCovidResponse: StatisticCovidResponse) {
    }

    override fun onGetStatus(statusResponse: StatusResponse) {
    }

    override fun onGetSymptom(symptomResponse: SymptomResponse) {
    }

    override fun onGetUser(user: UserResponse) {
    }

    override fun onGetUserHealths(healthResponse: HealthResponse) {

    }

    override fun onInsertHealth(healthResponse: HealthResponse) {

    }

    override fun onServerConnected() {
        Log.e(TAG, "onServerConnected: vao day", )
    }

    override fun onUpdateUser(user: UserResponse) {
    }

    override fun onUserSignIn(authResponse: AuthResponse) {
    }

    override fun onUserSignUp(authResponse: AuthResponse) {
        Log.e(TAG, "onUserSignUp: vao day", )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        service.addCallback(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
//        bankServiceController.removeCallback(this)
    }
}