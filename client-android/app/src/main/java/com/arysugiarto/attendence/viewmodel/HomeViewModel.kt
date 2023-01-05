package com.arysugiarto.attendence.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arysugiarto.attendence.util.BaseViewModel
import com.arysugiarto.attendence.data.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.data.remote.model.*

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    homeRepository: HomeRepository
) : BaseViewModel(application) {
    private val repository = homeRepository


    private var _register: MutableLiveData<Result<RegisterModel>> = MutableLiveData()
    val register: LiveData<Result<RegisterModel>> get() = _register

    private var _employee: MutableLiveData<Result<EmployeResponse>> = MutableLiveData()
    val employee: LiveData<Result<EmployeResponse>> get() = _employee
    private var _update: MutableLiveData<Result<UpdateModel>> = MutableLiveData()
    val update: LiveData<Result<UpdateModel>> get() = _update
    private var _delete: MutableLiveData<Result<Any>> = MutableLiveData()
    val delete: LiveData<Result<Any>> get() = _delete



    fun requestEmployee() =
        repository.requestEmployees()
            .onEach { result ->
                _employee.value = result
            }.launchIn(viewModelScope)


    fun requestUpdate(updateModel: UpdateModel, employeId: String) =
        repository.update (updateModel, employeId).onEach { result ->
            _update.value = result
        }.launchIn(viewModelScope)


    fun requestDelete(employeId: String) =
        repository.delete(employeId)
            .onEach { result ->
                _delete.value = result
            }.launchIn(viewModelScope)

    fun requestRegister(registerModel: RegisterModel) =
        repository.register (registerModel).onEach { result ->
            _register.value = result
        }.launchIn(viewModelScope)




}