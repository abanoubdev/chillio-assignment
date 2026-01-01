package net.chillio.network.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.chillio.network.api.EmployeeApi
import net.chillio.network.data.model.EmployeeListResponse
import net.chillio.network.di.IoDispatcher
import net.chillio.network.domain.EmployeeListDataSource
import net.chillio.network.utils.ApiResult
import javax.inject.Inject

internal class EmployeeListDataSourceImpl
@Inject constructor(
    val employeeApi: EmployeeApi,
    @IoDispatcher private val io: CoroutineDispatcher
) : EmployeeListDataSource {

    override fun getEmployeeList(): Flow<ApiResult<EmployeeListResponse>> =
        flow {
            emit(ApiResult.Loading)
            val response = employeeApi.getEmployeeList()
            if (response.employees.isEmpty()) {
                emit(ApiResult.Empty)
            } else {
                emit(ApiResult.Success(response))
            }
        }.catch { e ->
            emit(ApiResult.Error(e))
        }.flowOn(io)
}