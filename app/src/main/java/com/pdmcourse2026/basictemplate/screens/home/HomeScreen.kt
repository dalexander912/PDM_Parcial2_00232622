package com.pdmcourse2026.basictemplate.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.components.OptionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navigateToResults: () -> Unit,
  viewModel: HomeViewModel = viewModel()
) {
  val options by viewModel.options.collectAsState()
  val loading by viewModel.loading.collectAsState()
  val error by viewModel.error.collectAsState()

  var votedOption by rememberSaveable { mutableStateOf(0) }

  Scaffold(
    topBar = {
      TopAppBar(
        colors = topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("RankeUca - Vota") },
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding)
    ) {
      if(loading) {
        CircularProgressIndicator()
      }

      LazyColumn(
        modifier = Modifier.weight(1f)
      ) {
        items(options) { option ->
          OptionCard(
            option,
            {
              viewModel.postVote(option.id)
              votedOption = option.id
            },
            votedOption
          )
          Spacer(Modifier.height(8.dp))
        }
      }
      Button(
        onClick = navigateToResults,
        modifier = Modifier.fillMaxWidth(),
        enabled = votedOption != 0
      ) {
        Text("Ir a resultados")
      }
    }
  }
}