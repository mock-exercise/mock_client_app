package com.example.clientapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.clientapp.R
import com.example.clientapp.app.MyApplication
import com.example.clientapp.base.Event
import com.example.clientapp.model.repository.MainRepository
import com.example.connectorlibrary.callback.CallbackConnector
import com.example.connectorlibrary.controller.ServiceControllerUser
import com.example.connectorlibrary.enitity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val service: ServiceControllerUser,
    private val repository: MainRepository
): AndroidViewModel(application), LifecycleObserver, CallbackConnector.CallbackConnectorUser {

    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }

    // Handle Event

    var eventLoading = MutableLiveData<Event<Boolean>>()
        private set

    var eventError = MutableLiveData<Event<String>>()
        private set

    private fun showLoading(value: Boolean) {
        eventLoading.value = Event(value)
    }

    private fun showError(errorString: String) {
        eventError.value = Event(errorString)
    }

    private val applicationContext = getApplication() as MyApplication

    // Server Request
    fun  insertHealth(health: Health)= viewModelScope.launch{
        repository.insertHealth(health)
    }

    // Server Response
    override fun onFailureResponse(failureResponse: FailureResponse) {
        when (failureResponse.requestCode){
            RequestCode.INSERT_HEALTH ->{
                when(failureResponse.responseCode){
                    ResponseCode.ERROR_INSERT_HEALTH ->
                        showError("OOPS! Server không thể thêm khai báo của bạn")
                }
            }
        }
    }

    override fun onGetActive(activeResponse: ActiveResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetGender(genderResponse: GenderResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetHistoryCovidVn(historyCovidResponse: HistoryCovidResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetHistoryCovidWorld(historyCovidResponse: HistoryCovidResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetStatisticCovidVn(statisticCovidVnResponse: StatisticCovidVnResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetStatisticCovidWorld(statisticCovidWorldResponse: StatisticCovidWorldResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetStatus(statusResponse: StatusResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetSymptom(symptomResponse: SymptomResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetUser(user: UserResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetUserHealths(healthResponse: HealthResponse) {
        TODO("Not yet implemented")
    }

    override fun onInsertHealth(healthResponse: HealthResponse) {
        when(healthResponse.responseCode){
            ResponseCode.OK -> MyApplication.showToast(applicationContext, R.string.success_insert_health)
        }
    }

    override fun onServerConnected() {
        TODO("Not yet implemented")
    }

    override fun onUpdateUser(user: UserResponse) {
        TODO("Not yet implemented")
    }

    override fun onUserSignIn(authResponse: AuthResponse) {
        TODO("Not yet implemented")
    }

    override fun onUserSignUp(authResponse: AuthResponse) {
        TODO("Not yet implemented")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.e(AuthViewModel.TAG, "onCreate: vao di")
        service.addCallback(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        service.removeCallback(this)
    }

}