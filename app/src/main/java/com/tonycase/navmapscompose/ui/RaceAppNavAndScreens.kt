@file:OptIn(ExperimentalMaterial3Api::class)

package com.tonycase.navmapscompose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tonycase.navmapscompose.ui.RaceAppScreen.Login
import com.tonycase.navmapscompose.ui.RaceAppScreen.RaceList
import com.tonycase.navmapscompose.ui.about.aboutScreen
import com.tonycase.navmapscompose.ui.leaderboard.leaderboardScreen
import com.tonycase.navmapscompose.ui.login.loginScreen
import com.tonycase.navmapscompose.ui.profile.profileScreen
import com.tonycase.navmapscompose.ui.racedetails.raceDetailsScreen
import com.tonycase.navmapscompose.ui.racelist.raceListScreen
import com.tonycase.navmapscompose.ui.racemap.raceMapScreen

/**
 * Enumeration of all the screens in the app, with static information to build their toolbars.
 */
enum class RaceAppScreen(
   val toolbarTitle: String,
   val toolbarActions: List<Action> = emptyList()
) {
   Login("Login"),
   RaceList("Race List", listOf(Action.About, Action.Profile)),
   Details("Race Details", listOf(Action.Leaderboard)),
   Map("Race Map", listOf(Action.Leaderboard)),
   Profile("Profile"),
   About("About"),
   Leaderboard("Leaderboard");

   val node = name
}

/**
 * Enumeration of all Actions available from the toolbar, with their associated icons.
 */
enum class Action(val icon: ImageVector) {
   About(Icons.Filled.Info),
   Profile(Icons.Filled.Person),
   Leaderboard(Icons.Filled.Leaderboard)
}

@Composable
fun RootScreenAndNav(
   navController: NavHostController = rememberNavController()
) {
   // Recompose whenever back stack changes
   val backStackEntry by navController.currentBackStackEntryAsState()
   val currentScreen = backStackEntry.toRaceAppScreen()

   // Action to take when a screen has no auth
   val onNoAuth = { navController.navigate(Login.name) }

   Scaffold(
      topBar = {
         AppBar(currentScreen, navController, backStackEntry)
      }
   ) {
      NavHost(
         navController = navController,
         startDestination = RaceList.node,
         modifier = Modifier.padding(it)
      ) {
         // Declaration of all the screens, with their non-toolbar nav actions.
         raceListScreen(
            onNoAuth = onNoAuth,
            onRaceChosen = { raceKey -> navController.navigate("${RaceAppScreen.Details.node}/${raceKey}/") }
         )
         raceDetailsScreen(
            onStartRace = { raceKey -> navController.navigate("${RaceAppScreen.Map.node}/${raceKey}/") }
         )
         raceMapScreen()
         leaderboardScreen()
         aboutScreen()
         loginScreen(
            onLogin = { navController.navigate(RaceList.node) }
         )
         profileScreen(onNoAuth)
      }
   }
}

@Composable
private fun AppBar(
   currentScreen: RaceAppScreen,
   navController: NavHostController,
   backStackEntry: NavBackStackEntry?
) {
   val backNavAvailable = currentScreen !in listOf(RaceList, Login)
   fun Action.onClick(): () -> Unit  = when (this) {
      Action.About -> {
         { launchAbout(navController) }
      }
      Action.Profile -> {
         { launchProfile(navController) }
      }
      Action.Leaderboard -> {
         {
            launchLeaderboard(
               navController,
               backStackEntry?.arguments?.getString("raceId")
                  ?: throw IllegalStateException("Race Id needed for Leaderboard")
            )
         }
      }
   }

   TopAppBar(
      title = { Text(currentScreen.toolbarTitle) },
      colors = TopAppBarDefaults.mediumTopAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
      navigationIcon = {
         if (backNavAvailable) {
            IconButton(onClick = { navController.navigateUp() }) {
               Icon(
                  imageVector = Icons.Filled.ArrowBack,
                  contentDescription = "Back"
               )
            }
         }
      },
      actions = {
         currentScreen.toolbarActions.forEach { action ->
            IconButton(onClick = action.onClick()) {
               Icon(
                  imageVector = action.icon,
                  contentDescription = "Action icon for ${action.name}"
               )
            }
         }
      },
   )
}

private fun launchAbout(navController: NavHostController) {
   navController.navigate(RaceAppScreen.About.node)
}

private fun launchProfile(navController: NavHostController) {
   navController.navigate(RaceAppScreen.Profile.node)
}

private fun launchLeaderboard(navController: NavHostController, raceKey: String) {
   navController.navigate("${RaceAppScreen.Leaderboard.node}/${raceKey}/")
}

private fun NavBackStackEntry?.toRaceAppScreen(): RaceAppScreen {
   val route = this?.destination?.route
   return if (route == null) {
      // default landing screen
      RaceList
   } else {
      RaceAppScreen.valueOf(route.replaceAfter('/', "").removeSuffix("/"))
   }
}
