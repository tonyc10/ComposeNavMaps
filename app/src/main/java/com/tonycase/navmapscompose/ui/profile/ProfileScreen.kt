package com.tonycase.navmapscompose.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tonycase.navmapscompose.domain.User
import com.tonycase.navmapscompose.repositories.Fixtures
import com.tonycase.navmapscompose.ui.RaceAppScreen
import com.tonycase.navmapscompose.ui.RacesTitle
import com.tonycase.navmapscompose.ui.theme.RaceAppTheme

fun NavGraphBuilder.profileScreen(
   onNoAuth: () -> Unit = { }
) {
   composable(RaceAppScreen.Profile.name) {
      val viewModel: ProfileViewModel = hiltViewModel()

      val user by viewModel.uiState.collectAsState()
      if (user == null) {
         onNoAuth()
      } else {
         ProfileScreen(
            user = user!!, // todo: fix
            onLogout = { viewModel.logout() }
         )
      }
   }
}

@Composable
fun ProfileScreen(
   user: User,
   onLogout: () -> Unit = { }
) {
   Column(
      modifier = Modifier.padding(16.dp),
   ) {
      RacesTitle("Your information")
      Spacer(modifier = Modifier.height(16.dp))
      Text("Username: ${user.name} ")
      Spacer(modifier = Modifier.height(8.dp))
      Text("Email: ${user.email} ")
      Spacer(modifier = Modifier.height(16.dp))
      Button(
         onClick = onLogout,
         shape = RoundedCornerShape(5.dp),
         modifier = Modifier.fillMaxWidth()
      ) {
         Text("Logout")
      }
   }
}

@Preview
@Composable
fun ProfilePreview() {
   RaceAppTheme {
      ProfileScreen(Fixtures.fakeUser())
   }
}