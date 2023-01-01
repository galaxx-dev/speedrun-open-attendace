package com.arysugiarto.attendence.data.source.callback

import com.arysugiarto.attendence.data.remote.model.EmployeResponse
import com.arysugiarto.attendence.data.remote.model.LoginResponse
import com.arysugiarto.attendence.data.remote.model.SurveySend
import kotlinx.coroutines.flow.Flow
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.data.remote.model.RegisterModel


interface HomeSourceCallback {
    fun register(registerModel: RegisterModel): Flow<Result<RegisterModel>>

    fun requestLogin(
        employeId: String,
        password: String,
        sessionId: String
    ): Flow<Result<LoginResponse>>

    fun requestEmployees(
    ): Flow<Result<EmployeResponse>>
}