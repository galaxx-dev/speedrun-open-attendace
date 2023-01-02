package com.arysugiarto.attendence.data.repositories

import com.arysugiarto.attendence.data.remote.model.RegisterModel
import com.arysugiarto.attendence.data.remote.model.UpdateModel
import com.arysugiarto.attendence.data.source.callback.HomeSourceCallback
import com.arysugiarto.attendence.data.source.data.HomeRemoteDataSource
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    homeRemoteDataSource: HomeRemoteDataSource
) : HomeSourceCallback {
    private val remoteDataSource = homeRemoteDataSource

    override fun register(registerModel: RegisterModel): Flow<com.arysugiarto.attendence.data.remote.Result<RegisterModel>> =
        remoteDataSource.registerEmployeeDataSource(registerModel)


    override fun requestLogin(employeId: String, password: String) =
        remoteDataSource.requestLogin(employeId, password)

    override fun requestEmployees() = remoteDataSource.requestEmployeeDataSource()

    override fun update(updateModel: UpdateModel, employeId: String) =
        remoteDataSource.updateEmployeeDataSource(updateModel, employeId)

    override fun delete(employeId: String): Flow<com.arysugiarto.attendence.data.remote.Result<Any>> =
        remoteDataSource.requestDeleteEmployeeDataSource(employeId)

}