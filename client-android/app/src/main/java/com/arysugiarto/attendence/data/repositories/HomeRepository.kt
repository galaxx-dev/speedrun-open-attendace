package com.arysugiarto.attendence.data.repositories

import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.data.remote.model.SurveySend
import com.arysugiarto.attendence.data.source.callback.HomeSourceCallback
import com.arysugiarto.attendence.data.source.data.HomeRemoteDataSource
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    homeRemoteDataSource: HomeRemoteDataSource
) : HomeSourceCallback {
    private val remoteDataSource = homeRemoteDataSource

    override fun register(registerModel: RegisterModel): Flow<com.arysugiarto.attendence.data.remote.Result<RegisterModel>> =
        remoteDataSource.registerEmployeeDataSource(registerModel)


    override fun requestLogin(employeId: String, password: String, sessionId: String) =
        remoteDataSource.requestLogin(employeId, password, sessionId)

    override fun requestEmployees() = remoteDataSource.requestEmployeeDataSource()

}