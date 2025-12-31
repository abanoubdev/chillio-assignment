package net.chillio.feature.employeelist.state

import net.chillio.network.data.model.Employee

data class EmployeeListViewState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isEmpty: Boolean = false,
    val employees: List<Employee> = emptyList(),
    val errorMessage: String? = null
)