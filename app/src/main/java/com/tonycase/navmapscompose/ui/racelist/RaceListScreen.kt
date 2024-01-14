package com.tonycase.navmapscompose.ui.racelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tonycase.navmapscompose.repositories.Fixtures
import com.tonycase.navmapscompose.ui.RacesTitle
import com.tonycase.navmapscompose.ui.RaceAppScreen
import com.tonycase.navmapscompose.ui.theme.NavMapsComposeDemoTheme

fun NavGraphBuilder.raceListScreen(
   onRaceChosen: (String) -> Unit,
   onNoAuth: () -> Unit
) {
   composable(RaceAppScreen.RaceList.name) {
      val viewModel: RaceListViewModel = hiltViewModel()
      val raceListState by viewModel.uiState.collectAsState()

      if (!raceListState.loggedIn) {
         onNoAuth()
      }

      RaceListScreen(
         uiState = raceListState,
         raceChosen = onRaceChosen
      )
   }
}

@Composable
fun RaceListScreen(
   uiState: RaceListUiState,
   raceChosen: (String) -> Unit
) {
   LazyColumn(
      modifier = Modifier.padding(horizontal = 0.dp),
      contentPadding = PaddingValues(vertical = 16.dp)
   ) {
      item { RacesTitle(
          text = "Choose your race!",
         modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 12.dp)
      )
      }
      item {
         Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 0.3.dp)
      }
      items(uiState.racesToShow.size) { index ->
         RaceItem(
            raceUiItem = uiState.racesToShow[index],
            onClick = {
               raceChosen(it)
            }
         )
         Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 0.3.dp)
      }
   }
}

@Composable
private fun RaceItem(
   raceUiItem: RaceUiItem,
   onClick: (String) -> Unit
) {
   Row(
      modifier = Modifier
         .fillMaxWidth()
         .clickable { onClick(raceUiItem.key) }
         .padding(vertical = 6.dp, horizontal = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically

   ) {
      Column {
         Text(
            text = raceUiItem.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
         )
         Text(
            text = raceUiItem.locName,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary
         )
      }
      Column(
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         Text(
            text = raceUiItem.waypointCount.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold
         )
         Text(
            text = "Gates",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.secondary
         )
      }
   }
}

@Preview
@Composable
fun RaceListScreenPreview() {
   val races = Fixtures.fakeRaceList()
   NavMapsComposeDemoTheme {
      RaceListScreen(
         uiState = RaceListUiState(
            loggedIn = true,
            races.map { it.toUiItem() }
         ),
         raceChosen = { }
      )
   }
}
