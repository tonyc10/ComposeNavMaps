package com.tonycase.navmapscompose.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonycase.navmapscompose.domain.User
import com.tonycase.navmapscompose.repositories.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
   val userRepo: UserRepo,
): ViewModel() {

   val uiState: StateFlow<User?> =
      userRepo.currentUser()
         .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), User.Unknown)

   fun logout() {
      userRepo.logout()
   }
}
