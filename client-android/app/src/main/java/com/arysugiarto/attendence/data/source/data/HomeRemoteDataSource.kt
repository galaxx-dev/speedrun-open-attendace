package com.arysugiarto.attendence.data.source.data

import com.arysugiarto.attendence.data.remote.api.ApiCallback
import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.util.flowResponse
import com.arysugiarto.attendence.util.gson
import com.arysugiarto.attendence.util.toJsonElement
import kotlinx.coroutines.flow.Flow

class HomeRemoteDataSource(callback: ApiCallback) {
    private val apiCallback = callback



    fun handleSendAbsen(surveySend: SurveySend) = flowResponse {
        val body = gson.toJsonElement {
            put("nama", surveySend.nama)
            put("pekerjaan", surveySend.pekerjaan)
        }
        apiCallback.requestSendAbsen( body)
    }

}