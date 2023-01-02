package com.arysugiarto.attendence.data.source.data

import com.arysugiarto.attendence.data.remote.api.ApiCallback
import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.data.remote.model.UpdateModel
import com.arysugiarto.attendence.util.flowResponse
import com.arysugiarto.attendence.util.gson
import com.arysugiarto.attendence.util.toJsonElement

class HomeRemoteDataSource(callback: ApiCallback) {
    private val apiCallback = callback

    fun requestLogin(employeId: String, password: String) =
        flowResponse {
            val body = gson.toJsonElement {
                put("employee_id", employeId)
                put("password", password)
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

    fun updateEmployeeDataSource(updateModel: UpdateModel, employeId: String) = flowResponse {
        val body = gson.toJsonElement {
            put("employee_id", updateModel.employeeId)
            put("fullname", updateModel.fullname)
            put("email", updateModel.email)
            put("phone", updateModel.phone)
            put("address", updateModel.address)
            put("gender", updateModel.gender)
        }
        apiCallback.update ( body, employeId)
    }

    fun requestDeleteEmployeeDataSource(employeId: String) =
        flowResponse {
            apiCallback.delete(employeId)
        }

}