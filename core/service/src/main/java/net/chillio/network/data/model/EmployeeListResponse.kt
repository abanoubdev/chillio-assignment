package net.chillio.network.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeListResponse(
    val employees: List<Employee?>?
)