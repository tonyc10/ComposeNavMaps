package com.tonycase.navmapscompose.repositories

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.tonycase.navmapscompose.domain.Race
import com.tonycase.navmapscompose.domain.User
import kotlinx.datetime.Instant

/**
 * Mock data used by the project.
 */
object Fixtures {
   fun fakeUser(): User = User("0", "jones@abc123.com", "Catherine Jones", Instant.parse("2000-10-31T01:30:00-05:00"))

   fun fakeUserList() = listOf(user4, user3, user2, user1)

   val user1 = User("0", "jones@abc123.com", "Catherine Jones", Instant.parse("2019-10-31T01:30:00-05:00"))
   val user2 =
      User("user2", "clooney@abc123.com", "George Clooney", Instant.parse("2022-10-31T01:30:00-05:00"))
   val user3 =
      User("user3", "benning@abc123.com", "Annette Benning", Instant.parse("2001-10-31T01:30:00-05:00"))
   val user4 = User("user4", "abbott@abc123.com", "Bud Abbott", Instant.parse("2020-10-31T01:30:00-05:00"))

   val wayPoints1 = listOf(
      LatLngBounds(LatLng(40.76969707900806, -73.97290935949933), LatLng(40.77004646928051, -73.97228239262965)),
      LatLngBounds(LatLng(40.82030090104081, -74.41926482328665), LatLng(40.83316256229081, -74.39985889321625)),
      LatLngBounds(LatLng(40.256543859529515, -80.3496158663006), LatLng(40.644951025147485, -79.72146096305508)),
      LatLngBounds(LatLng(44.92545739466434, -93.37964950160043), LatLng(45.04555496887952, -93.16449176681147)),
      LatLngBounds(LatLng(43.5282640220219, -111.19353518789629), LatLng(43.9282640220219, -109.77830877410538)),
      LatLngBounds(LatLng(37.76805507067233, -122.47702527473832), LatLng(37.76946142502831, -122.47413634884373))
   )
   val boundingBox1 = LatLngBounds(LatLng(34.6, -123.5), LatLng(47.1, -72.8))

   val race1 = Race(
      "00",
      "The Great Race",
      "user2",
      "George Clooney",
      boundingBox1,
      "West from New York",
      wayPoints1,
      Instant.parse("2020-03-15T01:30:00-05:00"),
      "You'll probably need a car for this cross country trip, or a bike with a lot of provisions.  Please obey all speed limits.",
      "https://a.cdn-hotels.com/gdcs/production81/d1247/c0664d9b-f990-44f2-8cfe-ed0541088c8a.jpg?impolicy=fcrop&w=1600&h=1066&q=medium"
   )

   val race2 = Race(
      "01", "Tour of Mill District", "user2", "George Clooney",
      boundingBox1, "Starts at Mill Ruins Park", wayPoints1,
      Instant.parse("2020-03-16T01:30:00-05:00"), "The gates aren't real in this one, fake data.",
      "https://cdn.shortpixel.ai/spai/q_lossy+w_900+to_webp+ret_img/www.treksplorer.com/wp-content/uploads/mill-district-minneapolis.jpg"
   )

   // Fill in minnehaha park waypoints.
   val wayPoints3 = listOf<LatLngBounds>(
      LatLngBounds(LatLng(45.000, -93.500), LatLng(45.001, -93.499)),
      LatLngBounds(LatLng(45.002, -93.500), LatLng(45.003, -93.499)),
      LatLngBounds(LatLng(45.004, -93.500), LatLng(45.005, -93.499)),
      LatLngBounds(LatLng(45.006, -93.500), LatLng(45.007, -93.499)),
      LatLngBounds(LatLng(45.008, -93.500), LatLng(45.009, -93.499))
   )

   val race3 = Race(
      "02", "Race in the Park",
      "user3", "Annette Benning",
      boundingBox1, "Minnehaha Park, Mpls", wayPoints1,
      Instant.parse("2021-03-15T01:30:00-05:00"),
      "All gates are in Minnehaha Park and accessible on foot.  Have fun!",
      "https://www.minnpost.com/wp-content/uploads/2021/05/MinnehahaFallsCouple640.jpg?w=640&strip=all"
   )

   val race4 = Race(
      "03",
      "Bike Trails!",
      "user4",
      "Bud Abbott",
      boundingBox1,
      "Twin cities",
      wayPoints1,
      Instant.parse("2022-03-15T01:30:00-05:00"),
      "This has fake gate data, currently.",
      "https://hikebiketravel.com/wp-content/uploads/2016/11/The-Mesabi-Trail-in-Minnesota-7.jpg"
   )

   val wayPoints5 = listOf(
      LatLngBounds(LatLng(45.000, -93.500), LatLng(45.001, -93.499)),
      LatLngBounds(LatLng(45.002, -93.500), LatLng(45.003, -93.499)),
      LatLngBounds(LatLng(45.004, -93.500), LatLng(45.005, -93.499)),
      LatLngBounds(LatLng(45.006, -93.500), LatLng(45.007, -93.499)),
      LatLngBounds(LatLng(45.008, -93.500), LatLng(45.009, -93.499))
   )

   val race5 = Race(
      "04",
      "Silly Fake Data",
      "user4",
      "Bud Abbott",
      boundingBox1,
      "Place in Minnesota",
      wayPoints5,
      Instant.parse("2023-03-15T01:30:00-05:00"),
      "More fake data.",
      "https://images.unsplash.com/photo-1523049673857-eb18f1d7b578?q=80&w=2575&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
   )

   fun fakeRaceList(): List<Race> = listOf(race3, race1, race2, race4, race5)
}
