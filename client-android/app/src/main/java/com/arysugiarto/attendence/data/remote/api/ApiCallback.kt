package com.arysugiarto.attendence.data.remote.api

import com.arysugiarto.attendence.data.remote.model.EmployeResponse
import com.arysugiarto.attendence.data.remote.model.LoginResponse
import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.util.Const
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiCallback {

    @POST(Const.NETWORK.Registration)
    suspend fun register(
        @Body body: JsonElement
    ): Response<RegisterModel>

    @POST(Const.NETWORK.Login)
    suspend fun requestLogin(
        @Body jsonElement: JsonElement
    ): Response<LoginResponse>

    @GET(Const.NETWORK.Employee)
    suspend fun requestEmployee(
    ): Response<EmployeResponse>
}
