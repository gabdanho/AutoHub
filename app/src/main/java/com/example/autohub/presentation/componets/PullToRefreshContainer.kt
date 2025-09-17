package com.example.autohub.presentation.componets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.autohub.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshContainer(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = refreshState,
        onRefresh = onRefresh,
        indicator = {
            Indicator(
                state = refreshState,
                isRefreshing = isRefreshing,
                color = AppTheme.colors.containerColor,
                containerColor = AppTheme.colors.white,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        },
        modifier = modifier
    ) {
        content()
    }
}