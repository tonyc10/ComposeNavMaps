package com.tonycase.navmapscompose.ui.racedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.tonycase.navmapscompose.ui.RaceAppScreen
import com.tonycase.navmapscompose.ui.racelist.RaceListViewModel
import com.tonycase.navmapscompose.ui.racelist.RaceUiItem

fun NavGraphBuilder.raceDetailsScreen(
   // events that can't be handled by VM, i.e. nav events
   onStartRace: (String) -> Unit
) {
   val route = "${RaceAppScreen.Details.name}/{raceId}/"
   composable(
      route,
      arguments = listOf(navArgument("raceId") { type = NavType.StringType })
   ) {
      val raceId = it.arguments?.getString("raceId") ?: throw IllegalStateException("Excpecting gameId argument")
      // We're cheating here, reusing RaceListViewModel, picking our data out
      // and filling in the rest of the screen with mock data.
      val viewModel: RaceListViewModel = hiltViewModel()
      val raceListState by viewModel.uiState.collectAsState()
      val details = raceListState.racesToShow.firstOrNull { it.key == raceId }

      // can add events handled by VM here.
      RaceDetailsScreen(
         uiState = details!!,
         onStartRace = { onStartRace(raceId) }
      )
   }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RaceDetailsScreen(
   uiState: RaceUiItem,
   onStartRace: () -> Unit = { }
) {
   Column(
      modifier = Modifier
         .fillMaxHeight()
         .fillMaxWidth()
         .padding(16.dp, 24.dp, 16.dp, 16.dp),
      verticalArrangement = Arrangement.SpaceBetween
   )
   {
      Column(
         modifier = Modifier.fillMaxWidth(),
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         Text(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.title,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
         )
         // Glide Image here.
         uiState.imageUrl?.let {
            GlideImage(
               model = it,
               contentDescription = "fake hunt picture",
               modifier = Modifier.padding(16.dp),
            )
         }
         Text(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            text = "Location",
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
         )
         Text(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.locName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
         )
         Text(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            text = uiState.aboutText,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
         )
         Text(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 4.dp),
            text = "${uiState.waypointCount} gates",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
         )
      }
      Button(
         modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .height(72.dp),
         onClick = onStartRace) {
         Text(
            text = "Start Race!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
         )
      }
   }
}

@Preview
@Composable
fun RaceDetailsPreview() {
   RaceDetailsScreen(
      uiState = RaceUiItem(
         title = "The Great Race",
         locName = "New York to San Francisco",
         waypointCount = 3,
         key = "-1",
         aboutText = "KML's are rather slow in loading, so changing it all the time might give a very lazy effect. Apart from that wouldn't i have to change the name constantly to make sure the old kml doesn't get cached"
      )
   )
}
