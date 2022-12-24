package com.arysugiarto.attendence.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arysugiarto.attendence.base.BaseViewModel
import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.data.repositories.HomeRepository
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

    fun requestSendAbsen(surveySend: SurveySend) =
        repository.survey(surveySend).onEach { result ->
            _send.value = result
        }.launchIn(viewModelScope)


}