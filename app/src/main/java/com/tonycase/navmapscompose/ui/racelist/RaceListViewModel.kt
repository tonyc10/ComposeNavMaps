package com.tonycase.navmapscompose.ui.racelist

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonycase.navmapscompose.domain.Race
import com.tonycase.navmapscompose.repositories.RaceRepo
import com.tonycase.navmapscompose.repositories.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceListViewModel @Inject constructor(
   val resources: Resources,
   val raceRepo: RaceRepo,
   val userRepo: UserRepo,
): ViewModel() {

   val loggedIn = userRepo.currentUser().value != null
   private val _uiState = MutableStateFlow(
      RaceListUiState(loggedIn, emptyList())
   )
   val uiState: StateFlow<RaceListUiState> = _uiState.asStateFlow()

   init {
      loadData()
   }

   private fun loadData() {
      viewModelScope.launch {
         val races = raceRepo.allRaces()
         _uiState.tryEmit(
            RaceListUiState(
               loggedIn = loggedIn,
               racesToShow = races.map { it.toUiItem() }
            )
         )
      }
   }
}

data class RaceListUiState(
   val loggedIn: Boolean,
   val racesToShow: List<RaceUiItem>
)

data class RaceUiItem(
   val title: String,
   val locName: String,
   val waypointCount: Int,
   val key: String,
   val aboutText: String,
   val imageUrl: String? = null
)

fun Race.toUiItem() = RaceUiItem(
   title = this.title,
   locName = this.locName,
   waypointCount = this.wayPoints.size,
   key = this.key,
   aboutText = this.intro,
   imageUrl = this.imageUrl
)