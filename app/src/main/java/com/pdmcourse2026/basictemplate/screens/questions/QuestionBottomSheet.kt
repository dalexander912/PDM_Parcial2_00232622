package com.pdmcourse2026.basictemplate.screens.questions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pdmcourse2026.basictemplate.data.models.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionBottomSheet(
  questionToEdit: Question?,
  onSave: (title: String) -> Unit,
  onEdit: (title: String) -> Unit,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState()
  var title by rememberSaveable { mutableStateOf(questionToEdit?.title ?: "") }

  val isValid = title.isNotBlank()

  ModalBottomSheet(
    sheetState = sheetState,
    onDismissRequest = onDismiss
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .padding(bottom = 32.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text =
          if(questionToEdit == null) "Nueva pregunta"
          else "Editando pregunta",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
      Text(
        text =
          if(questionToEdit == null) "Agrega un título para que aparezca en la lista."
          else "Edita el título para actualizar la lista",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        label = { Text("Título de la pregunta") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 3
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        TextButton(onClick = onDismiss) { Text("Cancelar") }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
          onClick = {
            if(questionToEdit == null) {
              onSave(title.trim())
              onDismiss()
            } else {
              onEdit(title.trim())
              onDismiss()
            }
          },
          enabled = isValid
        ) {
          Text("Guardar")
        }
      }
    }
  }
}