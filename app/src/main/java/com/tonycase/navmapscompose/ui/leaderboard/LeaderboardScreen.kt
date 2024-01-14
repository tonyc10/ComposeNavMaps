package com.tonycase.navmapscompose.ui.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tonycase.navmapscompose.domain.User
import com.tonycase.navmapscompose.repositories.Fixtures
import com.tonycase.navmapscompose.ui.RacesTitle
import com.tonycase.navmapscompose.ui.RaceAppScreen
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.time.Duration
import kotlin.time.DurationUnit.SECONDS
import kotlin.time.toDuration

/**
 * Super simple leaderboard screen.  All fake data.
 */
fun NavGraphBuilder.leaderboardScreen(
   // events that can't be handled by VM, i.e. nav events
) {
   composable(
      "${RaceAppScreen.Leaderboard.name}/{raceId}/",
      arguments = listOf(navArgument("raceId") { type = NavType.StringType })
   ) {
      val raceId = it.arguments?.getString("raceId") ?: throw IllegalStateException("Excpecting gameId argument")
      // val viewModel: RaceListViewModel = hiltViewModel()
      // val raceListState by viewModel.uiState.collectAsState()

      val raceLeaderboardUiState = LeaderboardUiState(
         raceId = raceId,
         leaders = Fixtures.fakeUserList().mapIndexed { index, user ->
            Leader(user = user, raceTime = (1000 + index * 100).toDuration(SECONDS))
         },
         userIndex = null
      )

      // can add events handled by VM here.
      LeaderboardScreen(
         uiState = raceLeaderboardUiState,
      )
   }
}

@Composable
fun LeaderboardScreen(
   uiState: LeaderboardUiState
) {
   LazyColumn(
      modifier = Modifier.padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 16.dp)
   ) {
      item { RacesTitle("Leaderboard for ${uiState.raceId}") }
      item { Spacer(modifier = Modifier.height(16.dp)) }
      items(uiState.leaders.size) { index ->
         LeaderboardItem(uiState.leaders[index])
      }
   }
}

@Composable
private fun LeaderboardItem(leader: Leader) {
   Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
   ) {
      Text(text = leader.user.name)
      Text(text = leader.raceTime.inWholeMinutes.toString() + " minutes")
   }
}

data class LeaderboardUiState(
   val raceId: String,
   val leaders: List<Leader>,
   val userIndex: Int?
)

data class Leader(
   val user: User,
   val raceTime: Duration,
   val raceDateTime: LocalDateTime = LocalDateTime(
      LocalDate(2023, 11, 22),
      LocalTime(12, 0)
   )
)

@Preview
@Composable
fun LeaderboardPreview() {
   LeaderboardScreen(uiState = LeaderboardUiState(
      raceId = "-1",
      leaders = Fixtures.fakeUserList().mapIndexed { index, user ->
          Leader(user = user, raceTime = (1000 + index*100).toDuration(SECONDS))
      },
      userIndex = null
   ))
}