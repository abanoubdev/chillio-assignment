package net.chillio.feature.employeelist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import net.chillio.feature.employeelist.state.EmployeeListViewState
import net.chillio.feature.employeelist.useCase.EmployeeListUseCase
import net.chillio.network.data.model.Employee

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val employeeListUseCase: EmployeeListUseCase
) : ViewModel() {

    private val refresh = MutableSharedFlow<Unit>(replay = 1)

    val state: StateFlow<EmployeeListViewState> =
        refresh
            .onStart { emit(Unit) }
            .flatMapLatest {
                employeeListUseCase()
                    .map { employees ->
                        validateResponse(employees)
                    }
                    .onStart {
                        emit(EmployeeListViewState(isLoading = true))
                    }
                    .catch { e ->
                        emit(
                            EmployeeListViewState(
                                isLoading = false,
                                errorMessage = e.message ?: "Unknown error"
                            )
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = EmployeeListViewState(isLoading = true)
            )

    private fun validateResponse(employees: List<Employee>): EmployeeListViewState =
        if (employees.isEmpty()) {
            EmployeeListViewState(
                isLoading = false,
                isEmpty = true,
                employees = emptyList(),
                errorMessage = null
            )
        } else {
            EmployeeListViewState(
                isLoading = false,
                isEmpty = false,
                employees = employees,
                errorMessage = null
            )
        }

    fun retry() {
        refresh.tryEmit(Unit)
    }
}