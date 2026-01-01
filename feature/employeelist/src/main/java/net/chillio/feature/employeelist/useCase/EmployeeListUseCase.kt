package net.chillio.feature.employeelist.useCase

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.chillio.network.data.model.Employee
import net.chillio.network.domain.EmployeeListDataSource
import net.chillio.network.utils.ApiResult

class EmployeeListUseCase @Inject constructor(
    private val dataSource: EmployeeListDataSource
) {
    operator fun invoke(): Flow<ApiResult<List<Employee>>> =
        dataSource.getEmployeeList().map { result ->
            when (result) {
                ApiResult.Loading -> ApiResult.Loading
                ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Error -> ApiResult.Error(result.throwable)
                is ApiResult.Success -> {
                    val employees = result.data.employees
                        .filterNotNull()
                    if (employees.isEmpty()) ApiResult.Empty
                    else ApiResult.Success(employees)
                }
            }
        }
}
