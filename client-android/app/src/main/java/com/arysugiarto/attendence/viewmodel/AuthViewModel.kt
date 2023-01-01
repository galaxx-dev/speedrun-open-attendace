package com.arysugiarto.attendence.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arysugiarto.attendence.base.BaseViewModel
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.data.remote.model.LoginResponse
import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.data.repositories.HomeRepository
import com.arysugiarto.attendence.util.getRandomCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    homeRepository: HomeRepository
) : BaseViewModel(application) {
    private val repository = homeRepository

    private var _register: MutableLiveData<Result<RegisterModel>> = MutableLiveData()
    val register: LiveData<Result<RegisterModel>> get() = _register

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
        employeid: String,
        password: String,
        sessionId: String
    ) = repository.requestLogin(employeid, password, sessionId)
        .onEach { result ->
            _login.value = result

            if (result is Result.Success) {
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


    fun requestRegister(registerModel: RegisterModel) =
        repository.register (registerModel).onEach { result ->
            _register.value = result
        }.launchIn(viewModelScope)




}