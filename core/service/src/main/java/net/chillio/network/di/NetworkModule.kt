package net.chillio.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import net.chillio.network.api.EmployeeApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.jvm.java

internal const val employeeListUrl =
    "https://gist.githubusercontent.com/NSDavidObject/6b8203590704ff331da2c910121d10fb/raw/4795d72e0f559afdb81f288d78ddbdab73342459/"

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(employeeListUrl)
            .client(okHttpClient)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideEmployeeListApi(retrofit: Retrofit): EmployeeApi {
        return retrofit.create(EmployeeApi::class.java)
    }
}