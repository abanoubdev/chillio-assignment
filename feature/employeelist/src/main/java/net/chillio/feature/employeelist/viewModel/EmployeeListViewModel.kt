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
import net.chillio.network.utils.ApiResult

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
                    .map { result ->
                        when (result) {
                            ApiResult.Loading -> EmployeeListViewState(isLoading = true)
                            ApiResult.Empty -> EmployeeListViewState(
                                isLoading = false,
                                employees = emptyList(),
                                isEmpty = true,
                                errorMessage = null
                            )
                            is ApiResult.Success -> EmployeeListViewState(
                                isLoading = false,
                                employees = result.data,
                                isEmpty = result.data.isEmpty(),
                                errorMessage = null
                            )
                            is ApiResult.Error -> EmployeeListViewState(
                                isLoading = false,
                                employees = emptyList(),
                                isEmpty = false,
                                errorMessage = result.throwable.message ?: "Unknown error"
                            )
                        }
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

    fun retry() {
        refresh.tryEmit(Unit)
    }
}