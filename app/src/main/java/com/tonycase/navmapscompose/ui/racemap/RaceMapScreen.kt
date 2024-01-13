package com.tonycase.navmapscompose.ui.racemap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.tonycase.navmapscompose.domain.Race
import com.tonycase.navmapscompose.repositories.Fixtures
import com.tonycase.navmapscompose.ui.RaceAppScreen
import kotlinx.coroutines.delay

fun NavGraphBuilder.raceMapScreen(
) {
   composable(
      "${RaceAppScreen.Map.name}/{raceId}/",
      arguments = listOf(navArgument("raceId") { type = NavType.StringType })
   ) {
      val raceId = it.arguments?.getString("raceId") ?: throw IllegalStateException("Excpecting raceId argument")

      // val viewModel: RaceListViewModel = hiltViewModel()
      // val raceListState by viewModel.uiState.collectAsState()
      val race = Fixtures.fakeRaceList().first { it.key == raceId }

      val raceMapState = race.toUiState()

      // can add events handled by VM here.
      RaceMapScreen(
         uiState = raceMapState,
      )
   }
}

@Composable
fun RaceMapScreen(
   uiState: RaceMapUiState
) {

//   val singapore = LatLng(1.35, 103.87)
   val startingPosition = uiState.wayPoints[0].center
   val cameraPositionState = rememberCameraPositionState {
      position = CameraPosition. fromLatLngZoom(startingPosition, 13f)
   }

   LaunchedEffect(uiState.boundingBox) {
      delay(2000)
      cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(uiState.boundingBox, 30))
   }

   GoogleMap(
      modifier = Modifier.fillMaxSize(),
      cameraPositionState = cameraPositionState
   ) {

      uiState.wayPoints.forEachIndexed { index, wayPoint ->
         Marker(
            state = MarkerState(wayPoint.center),
            title = "Gate + ${index+1}",
            snippet = "Race waypoint."
         )
         Polygon(
            points = wayPoint.toPolygonPoints(),
            fillColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
            strokeColor = MaterialTheme.colorScheme.primary,
            strokeWidth = 5f,
         )
      }
      Polygon(
         points = HALF_WORLD_FILL_PTS,
         fillColor = Color.Black.copy(alpha = 0.1f),
         holes = listOf(uiState.boundingBox.toPolygonPoints()),
         strokeColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
         strokeWidth = 8f,
         strokeJointType = JointType.ROUND
      )
      Polyline(
         points = uiState.wayPoints.map { it.center },
         width = 3f,
      )
   }
}

private val HALF_WORLD_FILL_PTS = listOf<LatLng>(
   LatLng(-60.0, -159.99), LatLng(-60.0, 19.99), LatLng(75.0, 19.99), LatLng(75.0, -159.99)
)

private fun Race.toUiState(): RaceMapUiState {
   return RaceMapUiState(
      title = this.title,
      boundingBox = this.boundingBox,
      wayPoints = this.wayPoints
   )
}

private fun LatLngBounds.toPolygonPoints() = listOf<LatLng>(
   southwest, LatLng(southwest.latitude, northeast.longitude), northeast, LatLng(northeast.latitude, southwest.longitude)
)

data class RaceMapUiState(
   val title: String,
   val boundingBox: LatLngBounds,
   val wayPoints: List<LatLngBounds>,
)

@Preview
@Composable
fun RaceMapPreview() {
   RaceMapScreen(uiState = Fixtures.race3.toUiState())
}