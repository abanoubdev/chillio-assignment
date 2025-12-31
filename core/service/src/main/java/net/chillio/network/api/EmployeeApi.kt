package net.chillio.network.api

import net.chillio.network.data.model.EmployeeListResponse
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface EmployeeApi {
    @Headers("Content-Type: application/json")
    @GET("takehome_employees.json")
    suspend fun getEmployeeList(): EmployeeListResponse
}