package com.tonycase.navmapscompose.ui.login

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
class LoginViewModel @Inject constructor(
   val userRepo: UserRepo,
): ViewModel() {

   private val _uiState = MutableStateFlow(Credentials())
   val uiState: StateFlow<Credentials> = _uiState.asStateFlow()

   suspend fun submitCredentials(): Boolean {
      val username = uiState.value.login
      val passwd = uiState.value.pwd
      val user = userRepo.createAccount(username, passwd, username)
      return user != null
   }

   fun usernameUpdated(username: String) {
      _uiState.update { it.copy(login = username) }
   }

   fun passwordUpdated(password: String) {
      _uiState.update { it.copy(pwd = password) }
   }
}

data class Credentials(
   var login: String = "",
   var pwd: String = "",
) {
   fun isNotEmpty(): Boolean {
      return login.isNotEmpty() && pwd.isNotEmpty()
   }
}
