package com.tonycase.navmapscompose.repositories

import com.google.android.gms.maps.model.LatLng
import com.tonycase.navmapscompose.domain.Race

/**
 * Repository for getting Race information.
 *
 * The implementation is fake but the API is legit enough.
 */
interface RaceRepo {
   suspend fun allRaces(): List<Race>
   suspend fun racesNearHere(location: LatLng): List<Race>
   suspend fun getRace(key: String): Race?
}

class RaceRepoImpl: RaceRepo {
   override suspend fun allRaces(): List<Race> {
      return Fixtures.fakeRaceList()
   }

   override suspend fun racesNearHere(location: LatLng): List<Race> {
      return allRaces()
   }

   override suspend fun getRace(key: String): Race? {
      val races = allRaces()
      return races.firstOrNull { it.key == key }
   }
}