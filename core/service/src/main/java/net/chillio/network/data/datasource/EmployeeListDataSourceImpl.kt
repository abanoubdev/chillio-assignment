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

/**
 * Concrete implementation of [EmployeeListDataSource] that fetches employee data
 * from the network using [EmployeeApi].
 *
 * This class is responsible for handling the API call, managing coroutine context switching
 * to an IO-optimized dispatcher, and wrapping the response (or any errors) in an [ApiResult]
 * sealed class, which is then exposed as a [Flow].
 *
 * @param employeeApi The Retrofit service interface for the employee API.
 * @param io The [CoroutineDispatcher] for running network operations on a background thread.
 */
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