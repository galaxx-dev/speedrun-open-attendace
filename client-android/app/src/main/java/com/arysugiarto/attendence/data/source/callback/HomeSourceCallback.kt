package com.arysugiarto.attendence.data.source.callback

import com.arysugiarto.attendence.data.remote.model.LoginResponse
import com.arysugiarto.attendence.data.remote.model.SurveySend
import kotlinx.coroutines.flow.Flow


interface HomeSourceCallback {
    fun submitAbsen(surveySend: SurveySend): Flow<com.arysugiarto.attendence.data.remote.Result<SurveySend>>

    fun requestLogin(
        employeId: String,
        password: String,
        sessionId: String
    ): Flow<com.arysugiarto.attendence.data.remote.Result<LoginResponse>>
}