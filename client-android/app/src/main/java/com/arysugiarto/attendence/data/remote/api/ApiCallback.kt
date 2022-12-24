package com.arysugiarto.attendence.data.remote.api

import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.util.Const
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCallback {

    @POST(Const.NETWORK.Survey)
    suspend fun requestSendAbsen(
        @Body body: JsonElement
    ): Response<SurveySend>

    @POST(Const.NETWORK.Survey)
    suspend fun requestLogin(
        @Body jsonElement: JsonElement
    ): Response<SurveySend>
}
