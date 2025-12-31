package net.chillio.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.chillio.network.data.datasource.EmployeeListDataSourceImpl
import net.chillio.network.domain.EmployeeListDataSource

@Module
@InstallIn(SingletonComponent::class)
internal interface EmployeeListDataSourceBindings {

    @Binds
    fun bindsEmployeeListDataSource(impl: EmployeeListDataSourceImpl): EmployeeListDataSource
}