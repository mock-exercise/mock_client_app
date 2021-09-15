package com.example.clientapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.clientapp.R
import com.example.clientapp.app.MyApplication
import com.example.clientapp.base.Event
import com.example.clientapp.model.repository.MainRepository
import com.example.clientapp.utils.FuncExtension.convertDateStringToTimestamp
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
) : AndroidViewModel(application), LifecycleObserver, CallbackConnector.CallbackConnectorUser {

    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }

    // Data From UI


    // Basic Data
    var liGender = MutableLiveData<List<Gender>>()
        private set

    var liStatus = MutableLiveData<List<Status>>()
        private set

    var liSymptom = MutableLiveData<List<Symptom>>()
        private set

    var userID: Int? = null

    // User Data
    var liUserHealths = MutableLiveData<List<Health>>()
        private set

    var userInformation = MutableLiveData<User>()
        private set

    //Handle Data From UI

    var declaredHealth = Health(list_symptom_id = listOf())

    fun resetDeclareHealth() {
        declaredHealth = Health(list_symptom_id = listOf())
    }

    fun addSymptom(idSymptom: Int) {

        val liSymptomID = declaredHealth.list_symptom_id.toMutableList()

        if (liSymptomID.contains(idSymptom)) {
            liSymptomID.remove(idSymptom)
        } else {
            liSymptomID.add(idSymptom)
        }
        declaredHealth.list_symptom_id = liSymptomID
    }

    fun setUserGender(id: Int){
        userInformation.value?.gender_id = id
        Log.e(TAG, "setUserGender: $id", )
    }

    fun setUserBirthdate(birth: String){
        userInformation.value?.birthday = birth.convertDateStringToTimestamp()
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

    fun getBasicData() {
        getGender()
        getSymptom()
        getStatus()
    }

    fun getGender() = viewModelScope.launch {
        repository.getGender()
    }

    fun getSymptom() = viewModelScope.launch {
        Log.e(TAG, "symptom: gui", )
        repository.getSymptom()
    }

    fun getStatus() = viewModelScope.launch {
        Log.e(TAG, "status: gui", )
        repository.getStatus()
    }

    fun insertHealth() = viewModelScope.launch {
        declaredHealth.declare_time = System.currentTimeMillis()
        userID?.let {
            declaredHealth.user_id = it
        }
        repository.insertHealth(declaredHealth)
    }

    fun getHistoryCovidVN() = viewModelScope.launch {
        repository.getHistoryCovidVN()
    }

    fun getHistoryCovidWorld() = viewModelScope.launch {
        repository.getHistoryCovidWorld()
    }

    fun getStatisticCovidVn() = viewModelScope.launch {
        repository.getHistoryCovidVN()
    }

    fun getStatisticCovidWorld() = viewModelScope.launch {
        repository.getStatisticCovidWorld()
    }

    fun getUserHealths() = viewModelScope.launch {
        repository.getUserHealths()
    }

    fun getUserInformation() = viewModelScope.launch {
        userID?.let {
            repository.getUser(it)
        } ?: run {
            Log.e(TAG, "getUserInformation: Lỗi không nhận được dữ liệu ID")
        }
    }

    fun updateUserInformation() = viewModelScope.launch{
        userInformation.value?.let { repository.updateUser(it) }
    }

    // Server Response
    override fun onFailureResponse(failureResponse: FailureResponse) {
        when (failureResponse.requestCode) {
            RequestCode.GET_GENDER -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_LIST_GENDER_NULL -> {
                        showError("OOPS! Nhận dữ liệu giới tính thất bại ")
                    }
                }
            }
            RequestCode.GET_STATUS -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_LIST_STATUS_NULL -> {
                        showError("OOPS! Nhận dữ liệu trạng thái thất bại ")
                    }
                }
            }
            RequestCode.GET_SYMPTOMS -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_LIST_SYMPTOMS_NULL -> {
                        showError("OOPS! Nhận dữ liệu triệu chứng thất bại ")
                    }
                }
            }
            RequestCode.INSERT_HEALTH -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_INSERT_HEALTH ->
                        showError("OOPS! Server không thể thêm khai báo của bạn")
                }
            }
            RequestCode.GET_HEALTHS -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_LIST_HEATHS_NOT_FOUND ->
                        showError("OOPS! Server không nhận đc dữ liệu sức khỏe của bạn")
                }
            }
            RequestCode.GET_USER -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_USER_NOT_FOUND -> {
                        showError("OOPS! Không nhận được dữ liệu thông tin user")
                    }
                }
            }
            RequestCode.UPDATE_USER -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_UPDATE_USER -> {
                        showError("OOPS! Không thể cập nhật user")
                    }
                }
            }
        }
    }

    override fun onGetGender(genderResponse: GenderResponse) {
        when (genderResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                liGender.value = genderResponse.listGender
                Log.e(TAG, "onGetGender: nhan thanh cong", )
            }
        }
    }

    override fun onGetStatus(statusResponse: StatusResponse) {
        when (statusResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                Log.e(TAG, "onGetstatus: nhan thanh cong", )
                liStatus.value = statusResponse.listStatuses
            }
        }
    }

    override fun onGetSymptom(symptomResponse: SymptomResponse) {
        when (symptomResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                Log.e(TAG, "onGetsymptom: nhan thanh cong", )
                liSymptom.value = symptomResponse.listSymptom
            }
        }
    }

    override fun onGetHistoryCovidVn(historyCovidResponse: HistoryCovidResponse) {

    }

    override fun onGetHistoryCovidWorld(historyCovidResponse: HistoryCovidResponse) {

    }

    override fun onGetStatisticCovidVn(statisticCovidVnResponse: StatisticCovidVnResponse) {

    }

    override fun onGetStatisticCovidWorld(statisticCovidWorldResponse: StatisticCovidWorldResponse) {

    }

    override fun onGetUserInformation(user: UserResponse) {
        when (user.responseCode) {
            ResponseCode.SUCCESS -> {
                MyApplication.showToast(applicationContext, R.string.success_get_user_information)
                user.user.let {
                    userInformation.value = it
                }
            }
        }
    }

    override fun onGetUserHealths(healthResponse: HealthResponse) {
        when (healthResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                MyApplication.showToast(applicationContext, R.string.success_get_user_health)
                liUserHealths.value = healthResponse.listHealths
            }
        }
    }

    override fun onInsertHealth(healthResponse: HealthResponse) {
        when (healthResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                MyApplication.showToast(applicationContext, R.string.success_insert_health)
                liUserHealths.value = healthResponse.listHealths
            }
        }
    }

    override fun onGetActive(activeResponse: ActiveResponse) {
    }



    override fun onUpdateUser(user: UserResponse) {
        when (user.responseCode) {
            ResponseCode.SUCCESS -> {
                MyApplication.showToast(applicationContext, R.string.success_update_user)
                getUserInformation()
            }
        }
    }

    override fun onUserSignIn(authResponse: AuthResponse) {
    }

    override fun onUserSignUp(authResponse: AuthResponse) {

    }

    override fun onServerConnected() {
        Log.e(TAG, "onServerConnected: connect thanh cong", )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.e(TAG, "addCallback: vao di")
        service.addCallback(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        service.removeCallback(this)
    }
}