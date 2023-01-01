package com.arysugiarto.attendence.data.source.data

import com.arysugiarto.attendence.data.remote.api.ApiCallback
import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.util.flowResponse
import com.arysugiarto.attendence.util.gson
import com.arysugiarto.attendence.util.toJsonElement
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

class HomeRemoteDataSource(callback: ApiCallback) {
    private val apiCallback = callback

    fun requestLogin(employeId: String, password: String, sessionId: String) =
        flowResponse {
            val body = gson.toJsonElement {
                put("employee_id", employeId)
                put("password", password)
                put("session_id", sessionId)
            }

            apiCallback.requestLogin(body)
        }


    fun registerEmployeeDataSource(registerModel: RegisterModel) = flowResponse {
        val body = gson.toJsonElement {
            put("employee_id", registerModel.employeeId)
            put("fullname", registerModel.fullname)
            put("email", registerModel.email)
            put("phone", registerModel.phone)
            put("address", registerModel.address)
            put("gender", registerModel.gender)
            put("password", registerModel.password)
        }
        apiCallback.register( body)
    }

    fun requestEmployeeDataSource() =
        flowResponse {
            apiCallback.requestEmployee()
        }


}