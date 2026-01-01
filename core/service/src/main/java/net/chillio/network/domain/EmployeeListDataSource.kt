package net.chillio.network.domain

import kotlinx.coroutines.flow.Flow
import net.chillio.network.data.model.EmployeeListResponse
import net.chillio.network.utils.ApiResult

interface EmployeeListDataSource {
    fun getEmployeeList(): Flow<ApiResult<EmployeeListResponse>>
}