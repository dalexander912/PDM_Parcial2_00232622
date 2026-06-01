package com.pdmcourse2026.basictemplate.screens.results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.components.ResultCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
  navigateBack: () -> Unit,
  viewModel: ResultViewModel = viewModel()
) {
  val options by viewModel.options.collectAsState()
  val loading by viewModel.loading.collectAsState()
  val error by viewModel.error.collectAsState()
  val isRefreshing by viewModel.refreshing.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.loadOptions()
  }

  Scaffold(
    topBar = {
      TopAppBar(
        colors = topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("Resultados") },
      )
    }
  ) { innerPadding ->
    PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = { viewModel.refreshOptions() },
      modifier = Modifier.padding(innerPadding)
    ) {
      if(loading) {
        CircularProgressIndicator()
      }

      Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
          modifier = Modifier.weight(1f)
        ) {
          items(options) { option ->
            ResultCard(option)
            Spacer(Modifier.height(8.dp))
          }
        }
        Button(navigateBack, modifier = Modifier.fillMaxWidth()) {
          Text("Nuevo (volver a votar)")
        }
      }
    }
  }
}