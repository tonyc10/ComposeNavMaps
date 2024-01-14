package com.tonycase.navmapscompose.domain

import kotlinx.datetime.Instant

/**
 * The state of a game in progress.
 */
data class GameState(
   val userId: String,
   val gameKey: String,
   val startTime: Instant,
   val endTime: Instant?,
   val currentGateway: Int,
   val gatewayStartTime: Instant,
)