package com.arysugiarto.attendence.data.remote.api

import com.arysugiarto.attendence.data.remote.model.*
import com.arysugiarto.attendence.util.Const
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*

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

    @PUT(Const.NETWORK.Edit)
    suspend fun update(
        @Body body: JsonElement,
        @Path("param") idEmploye: String
    ): Response<UpdateModel>

    @HTTP(
        method = "DELETE",
        path = Const.NETWORK.Delete,
        hasBody = true
    )
    suspend fun delete(
        @Path("param") idEmploye: String,
    ): Response<Any>

}
