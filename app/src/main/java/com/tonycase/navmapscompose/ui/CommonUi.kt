package com.tonycase.navmapscompose.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RacesTitle(text: String, modifier: Modifier = Modifier.fillMaxWidth()) {
   Text(
      modifier = modifier,
      text = text,
      style = MaterialTheme.typography.titleLarge,
      color = MaterialTheme.colorScheme.primary,
      fontWeight = FontWeight.SemiBold,
      textAlign = TextAlign.Center
   )
}

