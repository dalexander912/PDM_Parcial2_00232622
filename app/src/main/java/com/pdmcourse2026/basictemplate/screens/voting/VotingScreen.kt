package com.pdmcourse2026.basictemplate.screens.voting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VotingScreen(
  viewModel: VotingViewModel = viewModel(factory = VotingViewModel .Factory),
  navigateToResults: () -> Unit,
  onBack: () -> Unit
) {
  val questions by viewModel.questions.collectAsStateWithLifecycle()
  val options by viewModel.options.collectAsStateWithLifecycle()
  val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
  val error by viewModel.error.collectAsStateWithLifecycle()

  // 1. Cargando: Room vacío y todavía esperando a la API
  if (questions.isEmpty() && isRefreshing) {
    Scaffold(topBar = { TopAppBar(title = { Text("Preguntas") }) }) { padding ->
      CircularProgressIndicator(modifier = Modifier.padding(padding))
    }
    return
  }

  // 2. Error sin cache: la API falló y no hay nada guardado
  if (questions.isEmpty() && error != null) {
    Scaffold(topBar = { TopAppBar(title = { Text("Preguntas") }) }) { padding ->
      Column(
        modifier = Modifier.fillMaxSize().padding(padding)
      ) {
        Text("$error")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { viewModel.refresh() }) { Text("Reintentar") }
      }
    }
    return
  }

  // 3. Datos: hay cache (con o sin internet)
  Scaffold(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
    topBar = {
      TopAppBar(
        title = { Text("Voto masivo") },
        navigationIcon = {
          IconButton(onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { innerPadding ->
    PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = { viewModel.refresh() },
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        if(questions.isEmpty()) {
          Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "Todavia no hay preguntas",
              style = MaterialTheme.typography.titleMedium
            )
          }
        } else {
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            items(items = questions, key = { it.id }) { question ->
              ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Text(question.title)
                for (option in options) {
                  if(option.questionId == question.id) {
                    Row() {
                      Button({}) { Text(option.name) }
                    }
                  }
                }
              }
            }
            item { Button(navigateToResults) { Text("Votar") } }
          }
        }
      }
    }
  }
}