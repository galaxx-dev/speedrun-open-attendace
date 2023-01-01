package com.arysugiarto.attendence.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arysugiarto.attendence.base.BaseViewModel
import com.arysugiarto.attendence.data.remote.model.EmployeResponse
import com.arysugiarto.attendence.data.remote.model.LoginResponse
import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.data.repositories.HomeRepository
import com.arysugiarto.attendence.util.getRandomCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.arysugiarto.attendence.data.remote.Result

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    homeRepository: HomeRepository
) : BaseViewModel(application) {
    private val repository = homeRepository


    private var _employee: MutableLiveData<Result<EmployeResponse>> = MutableLiveData()
    val employee: LiveData<Result<EmployeResponse>> get() = _employee

    fun requestEmployee() =
        repository.requestEmployees()
            .onEach { result ->
                _employee.value = result
            }.launchIn(viewModelScope)



}