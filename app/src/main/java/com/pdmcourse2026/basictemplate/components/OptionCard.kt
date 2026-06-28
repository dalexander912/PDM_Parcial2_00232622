package com.pdmcourse2026.basictemplate.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pdmcourse2026.basictemplate.data.models.Option

@Composable
fun OptionCard(
  option: Option,
  onClick: () -> Unit,
  votedOption: Int
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable { onClick() },
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(modifier = Modifier.padding(8.dp)) {
      AsyncImage(
        model = option.imageUrl,
        contentDescription = option.value,
        modifier = Modifier
          .size(width = 80.dp, height = 120.dp)
          .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
      )
      Spacer(Modifier.width(16.dp))
      Column(
        verticalArrangement = Arrangement.SpaceEvenly
      ) {
        Text(option.value, fontWeight = FontWeight.ExtraBold)
        Text("Toca para votar")
      }
      Spacer(Modifier.width(16.dp))
      if(votedOption == option.id) {
        Text("Tu voto", fontWeight = FontWeight.ExtraBold)
      }
    }
  }
}