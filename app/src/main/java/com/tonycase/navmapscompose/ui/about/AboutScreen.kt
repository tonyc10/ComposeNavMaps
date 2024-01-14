package com.tonycase.navmapscompose.ui.about

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tonycase.navmapscompose.ui.RaceAppScreen
import com.tonycase.navmapscompose.ui.RacesTitle

/**
 * Super simple About screen.  No View model.
 */
fun NavGraphBuilder.aboutScreen(
) {
   composable(RaceAppScreen.About.name) {
      AboutScreen()
   }
}

@Composable
fun AboutScreen() {
   LazyColumn(
      modifier = Modifier.padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 16.dp)
   ) {
      item { RacesTitle("The Waypoint Race") }
      item { Spacer(modifier = Modifier.height(16.dp)) }
      item { Text("Salmagundi pillage avast aft gabion marooned sloop draft gibbet dance the hempen jig. Fathom hempen halter ye main sheet barkadeer cog take a caulk clipper bilge port. Hail-shot Privateer hearties crack Jennys tea cup nipperkin lugger Jolly Roger Chain Shot shrouds bilged on her anchor.\n" +
         "\n" +
         "Lateen sail boom haul wind clap of thunder gibbet no prey, no pay nipper ahoy fire ship Privateer. Coffer measured fer yer chains spike barkadeer American Main sutler maroon clipper log Davy Jones' Locker. Landlubber or just lubber grog Plate Fleet gally mutiny bowsprit quarter snow man-of-war run a shot across the bow.")}
   }
}

@Preview
@Composable
fun AboutPreview() {
   AboutScreen()
}