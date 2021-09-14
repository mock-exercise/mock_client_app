package com.example.clientapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.clientapp.R
import com.example.clientapp.app.MyApplication
import com.example.clientapp.base.Event
import com.example.clientapp.model.repository.AuthRepository
import com.example.connectorlibrary.callback.CallbackConnector
import com.example.connectorlibrary.controller.ServiceControllerUser
import com.example.connectorlibrary.enitity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val service: ServiceControllerUser,
    private val repository: AuthRepository
) : AndroidViewModel(application), LifecycleObserver, CallbackConnector.CallbackConnectorUser {

    companion object {
        val TAG: String = AuthViewModel::class.java.simpleName
    }

    var mInputPhoneNumber: String = ""

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

    // Handle Datastore

    private fun saveAuthToken(token: Int) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

    // Server Request
    fun registerAccount(userUser: User) = viewModelScope.launch {
        showLoading(true)
        Log.e(TAG, "registerAccount: đăng ký", )
        repository.registerUserAccount(userUser)
    }

    fun loginAccount(phoneNumber: String) = viewModelScope.launch {
        showLoading(true)
        repository.loginUser(phoneNumber)
        mInputPhoneNumber = phoneNumber
    }

    // Server Response
    override fun onFailureResponse(failureResponse: FailureResponse) {
        showLoading(false)
        when (failureResponse.requestCode) {
            RequestCode.SIGN_IN_REQ -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_SIGN_IN_USER_NOT_FOUND -> {
                        showError("Tài khoản không tồn tại")
                    }
                }
            }
            RequestCode.SIGN_UP_REQ -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_SIGN_UP_WITH_USER_EXISTS -> {
                        showError("Số điện thoại đã được đăng ký! Bạn hãy đăng ký bằng số điện thoại khác ")
                    }
                    ResponseCode.ERROR_SIGN_UP -> {
                        showError("Lỗi đăng ký !!")
                    }
                }
            }
        }
    }

    override fun onGetActive(activeResponse: ActiveResponse) {
    }

    override fun onGetGender(genderResponse: GenderResponse) {
    }

    override fun onGetHistoryCovidVn(historyCovidResponse: HistoryCovidResponse) {
    }

    override fun onGetHistoryCovidWorld(historyCovidResponse: HistoryCovidResponse) {
    }

    override fun onGetStatisticCovidVn(statisticCovidVnResponse: StatisticCovidVnResponse) {
    }

    override fun onGetStatisticCovidWorld(statisticCovidWorldResponse: StatisticCovidWorldResponse) {
    }

    override fun onGetStatus(statusResponse: StatusResponse) {
    }

    override fun onGetSymptom(symptomResponse: SymptomResponse) {
    }


    override fun onGetUserHealths(healthResponse: HealthResponse) {

    }

    override fun onGetUserInformation(user: UserResponse) {

    }

    override fun onInsertHealth(healthResponse: HealthResponse) {

    }

    override fun onServerConnected() {
        Log.e(TAG, "onServerConnected: Đã connect đến server")
    }

    override fun onUpdateUser(user: UserResponse) {
    }

    override fun onUserSignIn(authResponse: AuthResponse) {
        showLoading(false)
        when (authResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                MyApplication.showToast(applicationContext, R.string.success_sign_in)
                saveAuthToken(authResponse.user_id)
            }
        }
    }

    override fun onUserSignUp(authResponse: AuthResponse) {
        showLoading(false)
        when (authResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                MyApplication.showToast(applicationContext, R.string.success_sign_up)
                saveAuthToken(authResponse.user_id)
//                savePhoneNumber(mInputPhoneNumber)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        service.addCallback(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        service.removeCallback(this)
    }
}