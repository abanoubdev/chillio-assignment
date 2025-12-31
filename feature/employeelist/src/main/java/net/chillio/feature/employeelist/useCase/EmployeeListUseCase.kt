package net.chillio.feature.employeelist.useCase

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.chillio.network.data.model.Employee
import net.chillio.network.domain.EmployeeListDataSource

class EmployeeListUseCase @Inject constructor(
    private val dataSource: EmployeeListDataSource
) {
    operator fun invoke(): Flow<List<Employee>> =
        dataSource.getEmployeeList()
            .map { it?.employees.orEmpty().filterNotNull() }
}