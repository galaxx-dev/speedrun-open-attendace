package com.arysugiarto.attendence.data.source.callback

import kotlinx.coroutines.flow.Flow
import com.arysugiarto.attendence.data.remote.Result
import com.arysugiarto.attendence.data.remote.model.*


interface HomeSourceCallback {
    fun register(registerModel: RegisterModel): Flow<Result<RegisterModel>>

    fun requestLogin(
        employeId: String,
        password: String
    ): Flow<Result<LoginResponse>>

    fun requestEmployees(
    ): Flow<Result<EmployeResponse>>

    fun update(updateModel: UpdateModel, employeId: String): Flow<Result<UpdateModel>>

    fun delete(employeId: String): Flow<Result<Any>>

}