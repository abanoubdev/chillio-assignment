package net.chillio.feature.employeelist.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.chillio.feature.employeelist.state.EmployeeListViewState
import net.chillio.feature.employeelist.viewModel.EmployeeListViewModel
import net.chillio.network.data.model.Employee
import net.chillio.ui.components.EmptyContent
import net.chillio.ui.components.ErrorContent
import net.chillio.ui.components.LoadingWheel
import net.chillio.ui.employeeList.EmployeeListItemRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeListComposable(
    modifier: Modifier,
    viewModel: EmployeeListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EmployeeListScreen(
        modifier = modifier,
        state = state,
        onRetry = viewModel::retry
    )
}

@Composable
fun EmployeeListScreen(
    modifier: Modifier,
    state: EmployeeListViewState,
    onRetry: () -> Unit
) {
    when {
        state.isLoading -> {
            LoadingContentUI(modifier)
        }

        state.isEmpty -> {
            EmptyContentUI(modifier)
        }

        state.errorMessage != null -> {
            ErrorContentUI(modifier, onRetry)
        }

        else -> {
            EmployeeListUI(
                employees = state.employees,
                modifier = modifier,
                isRefreshing = state.isRefreshing,
                onRefresh = onRetry
            )
        }
    }
}

@Composable
fun EmptyContentUI(modifier: Modifier) {
    EmptyContent(modifier)
}

@Composable
fun ErrorContentUI(modifier: Modifier, onRetry: () -> Unit) {
    ErrorContent(modifier = modifier, onRetry = onRetry)
}

@Composable
fun LoadingContentUI(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingWheel()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeListUI(
    employees: List<Employee>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = employees,
                key = { it.uuid }
            ) { employee ->
                EmployeeListItemRow(
                    imageUrl = employee.photo_url_small,
                    name = employee.full_name,
                    team = employee.team
                )
                HorizontalDivider()
            }
        }
    }
}