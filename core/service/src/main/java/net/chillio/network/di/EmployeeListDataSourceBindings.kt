package net.chillio.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.chillio.network.data.datasource.EmployeeListDataSourceImpl
import net.chillio.network.domain.EmployeeListDataSource

/**
 * Dagger Hilt module for providing bindings for the employee list data sources.
 * This interface defines how to provide an instance of [EmployeeListDataSource]
 * when requested, by binding it to its concrete implementation, [EmployeeListDataSourceImpl].
 *
 * It is installed in the [SingletonComponent], meaning the provided data source
 * will be a singleton for the entire application lifetime.
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface EmployeeListDataSourceBindings {

    @Binds
    fun bindsEmployeeListDataSource(impl: EmployeeListDataSourceImpl): EmployeeListDataSource
}