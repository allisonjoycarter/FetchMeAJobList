package com.example.fetchmeajoblist.ui.hiringList

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fetchmeajoblist.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiringListScreen(
    viewModel: HiringListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    val loading by viewModel.loading.collectAsState()
    val hiringItems by viewModel.hiringItems.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.errors.collect {
            // Normally we'd want an actionable error here.
            // For simplicity, we are using a basic error message.
            val result = snackBarHostState.showSnackbar(
                message = context.getString(R.string.an_unexpected_error_occurred),
                actionLabel = context.getString(R.string.retry)
            )

            when (result) {
                SnackbarResult.ActionPerformed -> {
                    viewModel.fetchHiringItems()
                }
                SnackbarResult.Dismissed -> {
                    // Do nothing
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.app_name)) })
        }
    ) { paddingValues ->
        Crossfade(targetState = loading) { isLoading ->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                HiringList(
                    hiringItems = hiringItems,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
