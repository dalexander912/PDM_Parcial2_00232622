package com.pdmcourse2026.basictemplate.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navigateToQuestion: () -> Unit,
  navigateToVote: () -> Unit
) {
  Scaffold(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
    topBar = {
      TopAppBar(
        title = { Text("RankeUCA") },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      ElevatedCard(
        modifier = Modifier.fillMaxWidth().height(64.dp)
          .clickable { navigateToQuestion() }
      ) {
        Text("Gestor de preguntas", modifier = Modifier.padding(16.dp))
      }
      Spacer(Modifier.height(32.dp))
      ElevatedCard(
        modifier = Modifier.fillMaxWidth().height(64.dp)
          .clickable(
            enabled = false,
            onClick = navigateToVote
          )
      ) {
        Text("Votar", modifier = Modifier.padding(16.dp))
      }
    }
  }
}