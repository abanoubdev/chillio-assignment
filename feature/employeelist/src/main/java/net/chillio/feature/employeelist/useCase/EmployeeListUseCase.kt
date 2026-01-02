package net.chillio.feature.employeelist.useCase

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.chillio.network.data.model.Employee
import net.chillio.network.domain.EmployeeListDataSource
import net.chillio.network.utils.ApiResult

class EmployeeListUseCase @Inject constructor(
    private val employeeListDataSource: EmployeeListDataSource
) {
    operator fun invoke(): Flow<ApiResult<List<Employee>>> =
        employeeListDataSource.getEmployeeList().map { apiResult ->
            when (apiResult) {
                ApiResult.Loading -> ApiResult.Loading
                ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Error -> ApiResult.Error(apiResult.throwable)
                is ApiResult.Success -> {
                    val employees = apiResult.data.employees
                        .filterNotNull()
                    if (employees.isEmpty()) ApiResult.Empty
                    else ApiResult.Success(employees)
                }
            }
        }
}
