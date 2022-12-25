package com.arysugiarto.attendence.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arysugiarto.attendence.base.BaseViewModel
import com.arysugiarto.attendence.data.remote.model.LoginResponse
import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.data.repositories.HomeRepository
import com.arysugiarto.attendence.util.getRandomCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    homeRepository: HomeRepository
) : BaseViewModel(application) {
    private val repository = homeRepository

    private var _send: MutableLiveData<com.arysugiarto.attendence.data.remote.Result<SurveySend>> = MutableLiveData()
    val send: LiveData<com.arysugiarto.attendence.data.remote.Result<SurveySend>> get() = _send

    private var _token: MutableLiveData<String> = MutableLiveData()
    val token: LiveData<String> get() = _token
    private var _login: MutableLiveData<com.arysugiarto.attendence.data.remote.Result<LoginResponse>> = MutableLiveData()
    val login: LiveData<com.arysugiarto.attendence.data.remote.Result<LoginResponse>> get() = _login
    private var _isUserLoggedOff: MutableLiveData<com.arysugiarto.attendence.data.remote.Result<Any>> = MutableLiveData()
    val isUserLoggedOff: LiveData<com.arysugiarto.attendence.data.remote.Result<Any>> get() = _isUserLoggedOff

    fun getUserAccessToken() = accessManager.access.onEach {
        _token.value = it.toString()
    }.launchIn(viewModelScope)

    fun requestLogin(
        email: String,
        password: String,
        sessionId: String
    ) = repository.requestLogin(email, password, sessionId)
        .onEach { result ->
            _login.value = result

            if (result is com.arysugiarto.attendence.data.remote.Result.Success) {
                val token = result.data?.payload?.token.orEmpty()

                accessManager.setAccess(token)
                _token.value = token
            }
        }.launchIn(viewModelScope)

    suspend fun setSessionId() {
        35.getRandomCharacters.let { sessionId ->
            accessManager.setSessionId(sessionId)
        }
    }


    fun requestSendAbsen(surveySend: SurveySend) =
        repository.submitAbsen (surveySend).onEach { result ->
            _send.value = result
        }.launchIn(viewModelScope)


}