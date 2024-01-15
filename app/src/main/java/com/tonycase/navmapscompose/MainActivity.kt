package com.tonycase.navmapscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tonycase.navmapscompose.ui.RootScreenAndNav
import com.tonycase.navmapscompose.ui.theme.RaceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      setContent {
         RaceAppTheme {
            RootScreenAndNav()
         }
      }
   }
}
