package com.tonycase.navmapscompose.domain

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

/**
 * Simple user object
 */
data class User(
   val id: String,
   val email: String,
   val name: String,
   val accountCreation: Instant
) {
   companion object {
      // hack for Loading state in Profile
      val Unknown: User = User("-1", "", "", Instant.DISTANT_PAST)
   }
}
