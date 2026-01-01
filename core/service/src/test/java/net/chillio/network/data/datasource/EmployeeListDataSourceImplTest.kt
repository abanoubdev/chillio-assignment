package net.chillio.network.data.datasource

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import net.chillio.network.api.EmployeeApi
import net.chillio.network.provider.employeesEmptyJson
import net.chillio.network.provider.employeesErrorJson
import net.chillio.network.provider.employeesSuccessJson
import net.chillio.network.utils.ApiResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class EmployeeListDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: EmployeeApi

    @Before
    fun setup() {

        server = MockWebServer()
        server.start()

        val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()

        api = retrofit.create(EmployeeApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }


    @Test
    fun `getEmployeeList emits Loading then Success and calls correct endpoint`() = runTest {

        val ioDispatcher = StandardTestDispatcher(testScheduler)
        val dataSource = EmployeeListDataSourceImpl(employeeApi = api, io = ioDispatcher)

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(employeesSuccessJson)
        )

        dataSource.getEmployeeList().test {
            ioDispatcher.scheduler.advanceUntilIdle()

            // 1️⃣ Verify emissions
            assertThat(awaitItem()).isEqualTo(ApiResult.Loading)

            val result = awaitItem()
            assertThat(result).isInstanceOf(ApiResult.Success::class.java)

            awaitComplete()
        }

        // 2️⃣ Verify HTTP request (AFTER collection)
        val request = server.takeRequest()
        assertThat(request.path).isEqualTo("/takehome_employees.json")
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.headers["Content-Type"]).contains("application/json")
    }


    @Test
    fun `getEmployeeList returns Success Response`() = runTest {

        val ioDispatcher = StandardTestDispatcher(testScheduler)
        val dataSource = EmployeeListDataSourceImpl(employeeApi = api, io = ioDispatcher)

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(employeesSuccessJson)
        )

        dataSource.getEmployeeList().test {
            ioDispatcher.scheduler.advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(ApiResult.Loading)
            val success = awaitItem() as ApiResult.Success
            assertThat(success.data.employees).isNotEmpty()
            awaitComplete()
        }
    }

    @Test
    fun `getEmployeeList returns Empty Response`() = runTest {

        val ioDispatcher = StandardTestDispatcher(testScheduler)
        val dataSource = EmployeeListDataSourceImpl(employeeApi = api, io = ioDispatcher)

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(employeesEmptyJson)
        )

        dataSource.getEmployeeList().test {
            ioDispatcher.scheduler.advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(ApiResult.Loading)
            val result = awaitItem()
            assertThat(result).isInstanceOf(ApiResult.Empty::class.java)
            awaitComplete()
        }
    }

    @Test
    fun `getEmployeeList returns Error Response`() = runTest {

        val ioDispatcher = StandardTestDispatcher(testScheduler)
        val dataSource = EmployeeListDataSourceImpl(employeeApi = api, io = ioDispatcher)

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(employeesErrorJson)
        )

        dataSource.getEmployeeList().test {
            ioDispatcher.scheduler.advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(ApiResult.Loading)
            val result = awaitItem()
            assertThat(result).isInstanceOf(ApiResult.Error::class.java)
            val throwable = (result as ApiResult.Error).throwable
            assertThat(throwable).isNotNull()
            awaitComplete()
        }
    }
}