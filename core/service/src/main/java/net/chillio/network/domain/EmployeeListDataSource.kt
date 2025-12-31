package net.chillio.network.domain

import kotlinx.coroutines.flow.Flow
import net.chillio.network.data.model.EmployeeListResponse

interface EmployeeListDataSource {
    fun getEmployeeList(): Flow<EmployeeListResponse?>
}