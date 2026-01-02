package net.chillio.network.api

import net.chillio.network.data.model.EmployeeListResponse
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Defines the REST API endpoints for fetching employee data.
 * This interface is used by Retrofit to generate the network request implementation.
 */
internal interface EmployeeApi {
    @Headers("Content-Type: application/json")
    @GET("takehome_employees.json")
    suspend fun getEmployeeList(): EmployeeListResponse
}