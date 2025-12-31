package net.chillio.network.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.chillio.network.api.EmployeeApi
import net.chillio.network.data.model.EmployeeListResponse
import net.chillio.network.di.IoDispatcher
import net.chillio.network.domain.EmployeeListDataSource
import javax.inject.Inject

internal class EmployeeListDataSourceImpl
@Inject constructor(
    val employeeApi: EmployeeApi,
    @IoDispatcher private val io: CoroutineDispatcher
) : EmployeeListDataSource {

    override fun getEmployeeList(): Flow<EmployeeListResponse?> = flow {
        emit(employeeApi.getEmployeeList())
    }.flowOn(io)
}