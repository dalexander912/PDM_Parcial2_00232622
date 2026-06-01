package com.pdmcourse2026.basictemplate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdmcourse2026.basictemplate.models.Option

@Composable
fun ResultCard(
  option: Option
) {
  Card(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxSize().padding(8.dp),
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      Text(option.name, fontWeight = FontWeight.ExtraBold)
      Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.End
      ) {
        Text(option.votes.toString(), fontSize = 24.sp)
        Text("votos", fontSize = 8.sp)
      }
    }
  }
}