package com.example.clientapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.clientapp.R
import com.example.clientapp.app.MyApplication
import com.example.clientapp.base.Event
import com.example.clientapp.data.model.StaticData
import com.example.clientapp.data.model.TodayStatic
import com.example.clientapp.data.model.ui.HealthGeneral
import com.example.clientapp.data.model.ui.NotifyType
import com.example.clientapp.data.repository.MainRepository
import com.example.clientapp.utils.Constant.DaysMakeYouInCase
import com.example.clientapp.utils.Constant.HealthyIndex
import com.example.clientapp.utils.Constant.NotifyDialogType
import com.example.clientapp.utils.Constant.StatusCovid
import com.example.clientapp.utils.Constant.HealthGeneralType
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
    var isEnableVNButton = MutableLiveData(false)
        private set

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

    fun setUserGender(id: Int) {
        userInformation.value?.gender_id = id
        Log.e(TAG, "setUserGender: $id")
    }

    fun setUserBirthdate(birth: String) {
        userInformation.value?.birthday = birth.convertDateStringToTimestamp()
    }

    var mHealthGeneralType = MutableLiveData(HealthGeneral())

    private fun checkHealthStatusGeneral() {
        val healthGeneralType: HealthGeneral
        val listUserHealth = liUserHealths.value

        if (listUserHealth.isNullOrEmpty()) {
            healthGeneralType = HealthGeneral(
                HealthGeneralType.SUGGEST,
                applicationContext.getString(R.string.suggest_not_initial_health_history)
            )
        } else {
            val isUserUnsafe = (listUserHealth.takeLast(DaysMakeYouInCase).count {
                !it.list_symptom_id.contains(HealthyIndex)
            } == DaysMakeYouInCase)

            healthGeneralType = if (isUserUnsafe) {
                HealthGeneral(
                    HealthGeneralType.UNSAFE,
                    applicationContext.getString(R.string.warning_health_history)
                )
            } else {
                HealthGeneral(
                    HealthGeneralType.SAFE,
                    applicationContext.getString(R.string.congratulation_health_history)
                )
            }
        }
        Log.e(TAG, "checkHealthStatusGeneral: ${healthGeneralType.message} ", )
        mHealthGeneralType.value = healthGeneralType

    }

    // Handle DataStore

    fun clearDataStore() = viewModelScope.launch {
        repository.clearDataStore()
    }

    // Handle Event

    var eventLoading = MutableLiveData<Event<Boolean>>()
        private set

    var eventNotify = MutableLiveData<Event<NotifyType>>()
        private set

    private fun showLoading(value: Boolean) {
        eventLoading.value = Event(value)
    }

    private fun showNotify(notifyType: NotifyType) {
        eventNotify.value = Event(notifyType)
    }

    private val applicationContext = getApplication() as MyApplication

    // Server Request

    fun getBasicData() {
        getGender()
        getSymptom()
        getStatus()
    }

    private fun getGender() = viewModelScope.launch {
        showLoading(true)
        repository.getGender()
    }

    private fun getSymptom() = viewModelScope.launch {
        repository.getSymptom()
    }

    private fun getStatus() = viewModelScope.launch {
        repository.getStatus()
    }

    fun insertHealth() = viewModelScope.launch {
        showLoading(true)
        declaredHealth.declare_time = System.currentTimeMillis()
        userID?.let {
            declaredHealth.user_id = it
        }
        repository.insertHealth(declaredHealth)
    }

    fun getHistoryCovidVN() = viewModelScope.launch {
        showLoading(true)
        repository.getHistoryCovidVN()
    }

    fun getHistoryCovidWorld() = viewModelScope.launch {
        repository.getHistoryCovidWorld()
    }

    fun getUserHealths() = viewModelScope.launch {
        repository.getUserHealths()
    }

    fun getUserInformationFromServer() = viewModelScope.launch {
        userID?.let {
            repository.getUser(it)
        } ?: run {
            Log.e(TAG, "getUserInformation: Lỗi không nhận được dữ liệu ID")
        }
    }

    fun updateUserInformation() = viewModelScope.launch {
        showLoading(true)
        userInformation.value?.let { repository.updateUser(it) }
    }

    // Server Response
    override fun onFailureResponse(failureResponse: FailureResponse) {
        showLoading(false)
        when (failureResponse.requestCode) {
            RequestCode.GET_GENDER -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_LIST_GENDER_NULL -> {
                        Log.e(TAG, "onFailureResponse: OOPS! Nhận dữ liệu giới tính thất bại")
                    }
                }
            }
            RequestCode.GET_STATUS -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_LIST_STATUS_NULL -> {
                        Log.e(TAG, "onFailureResponse: OOPS! Nhận dữ liệu trạng thái thất bại")
                    }
                }
            }
            RequestCode.GET_SYMPTOMS -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_LIST_SYMPTOMS_NULL -> {
                        Log.e(TAG, "onFailureResponse: OOPS! Nhận dữ liệu triệu chứng thất bại ")
                    }
                }
            }
            RequestCode.INSERT_HEALTH -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_INSERT_HEALTH ->
                        showNotify(
                            NotifyType(
                                type = NotifyDialogType.ERROR,
                                applicationContext.getString(R.string.failure_insert_health)
                            )
                        )
                }
            }
            RequestCode.GET_HEALTHS -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_LIST_HEATHS_NOT_FOUND ->
                        Log.e(
                            TAG,
                            "onFailureResponse: OOPS! Server không nhận đc dữ liệu sức khỏe của bạn "
                        )
                }
            }
            RequestCode.GET_USER -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_USER_NOT_FOUND -> {
                        Log.e(
                            TAG,
                            "onFailureResponse: OOPS! Không nhận được dữ liệu thông tin user "
                        )
                    }
                }
            }
            RequestCode.UPDATE_USER -> {
                when (failureResponse.responseCode) {
                    ResponseCode.ERROR_UPDATE_USER -> {
                        Log.e(TAG, "onFailureResponse: OOPS! Không thể cập nhật user ")
                    }
                }
            }
        }
    }

    override fun onGetGender(genderResponse: GenderResponse) {
        showLoading(false)
        when (genderResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                liGender.value = genderResponse.listGender
            }
        }
    }

    override fun onGetStatus(statusResponse: StatusResponse) {
        showLoading(false)
        when (statusResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                liStatus.value = statusResponse.listStatuses
            }
        }
    }

    override fun onGetSymptom(symptomResponse: SymptomResponse) {
        showLoading(false)
        when (symptomResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                liSymptom.value = symptomResponse.listSymptom
            }
        }
    }

    var mLiHistoryCovid = MutableLiveData<List<HistoryCovid>>()
        private set

    val mTodayStaticAll = MutableLiveData<MutableList<StaticData>>().apply {
        value?.add(StaticData(StatusCovid.CASE.numberIndex))
        value?.add(StaticData(StatusCovid.DEATH.numberIndex))
        value?.add(StaticData(StatusCovid.RECOVERED.numberIndex))
    }

    override fun onGetHistoryCovidVn(historyCovidResponse: HistoryCovidResponse) {
        showLoading(false)
        when (historyCovidResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                Log.e(TAG, "onGetHistoryCovidVn: nhan thanh cong")
                val historyCovid = historyCovidResponse.listHistoryCovid
                val lastInx = historyCovid.size - 1

                mTodayStaticAll.value = historyCovid.map {
                    StaticData(
                        it.status,
                        TodayStatic(
                            it.listPeopleInDay[lastInx].people,
                            it.listPeopleInDay[lastInx].people - it.listPeopleInDay[lastInx - 1].people
                        )
                    )
                }.toMutableList()


                var index = 1
                val a = historyCovid[0].listPeopleInDay.dropLast(1).map {
                    PeopleInDay(it.day, historyCovid[0].listPeopleInDay[index++].people - it.people)
                }

                historyCovid[0].listPeopleInDay = a
                mLiHistoryCovid.value = historyCovid
            }
        }
    }

    override fun onGetHistoryCovidWorld(historyCovidResponse: HistoryCovidResponse) {
        showLoading(false)
        when (historyCovidResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                Log.e(TAG, "onGetHistoryCovidWorld: nhan thanh cong")
                val historyCovid = historyCovidResponse.listHistoryCovid
                val lastInx = historyCovid.size - 1

                mTodayStaticAll.value = historyCovid.map {
                    StaticData(
                        it.status,
                        TodayStatic(
                            it.listPeopleInDay[lastInx].people,
                            it.listPeopleInDay[lastInx].people - it.listPeopleInDay[lastInx - 1].people
                        )
                    )
                }.toMutableList()


                var index = 1
                val a = historyCovid[0].listPeopleInDay.dropLast(1).map {
                    PeopleInDay(it.day, historyCovid[0].listPeopleInDay[index++].people - it.people)
                }

                historyCovid[0].listPeopleInDay = a
                mLiHistoryCovid.value = historyCovid
            }
        }
    }

    override fun onGetUserInformation(user: UserResponse) {
        showLoading(false)
        when (user.responseCode) {
            ResponseCode.SUCCESS -> {
                user.user.let {
                    userInformation.value = it
                }
            }
        }
    }

    override fun onGetUserHealths(healthResponse: HealthResponse) {
        showLoading(false)
        when (healthResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                liUserHealths.value = healthResponse.listHealths
                checkHealthStatusGeneral()
            }
        }
    }

    override fun onInsertHealth(healthResponse: HealthResponse) {
        showLoading(false)
        when (healthResponse.responseCode) {
            ResponseCode.SUCCESS -> {
                liUserHealths.value = healthResponse.listHealths
                checkHealthStatusGeneral()

                showNotify(
                    NotifyType(
                        type = NotifyDialogType.SUCCESS,
                        applicationContext.getString(R.string.success_insert_health)
                    )
                )
            }
        }
    }

    override fun onUpdateUser(user: UserResponse) {
        showLoading(false)
        when (user.responseCode) {
            ResponseCode.SUCCESS -> {
                MyApplication.showToast(applicationContext, R.string.success_update_user)
                getUserInformationFromServer()
            }
        }
    }

    override fun onUserSignIn(authResponse: AuthResponse) {
    }

    override fun onUserSignUp(authResponse: AuthResponse) {

    }

    override fun onServerConnected() {
        showLoading(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        showLoading(true)
        service.addCallback(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        service.removeCallback(this)
    }
}