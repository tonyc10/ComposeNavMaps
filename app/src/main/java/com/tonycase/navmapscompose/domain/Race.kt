package com.tonycase.navmapscompose.domain

import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.datetime.Instant

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 12/22/23.
 */
data class Race(
   val key: String,
   val title: String,
   val authorId: String?,
   val authorName: String = "",
   val boundingBox: LatLngBounds,
   val locName: String,
   val wayPoints: List<LatLngBounds>,
   val creationDate: Instant,
   val intro: String,
   val imageUrl: String? = null
)
