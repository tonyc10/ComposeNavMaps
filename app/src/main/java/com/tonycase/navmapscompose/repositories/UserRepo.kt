package com.tonycase.navmapscompose.repositories

import com.tonycase.navmapscompose.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock

/**
 * Repository for getting User information.
 *
 * The implementation is fake but the API is legit enough.
 *
 * If the current user is null, that indicates we do not currently have auth.
 */
interface UserRepo {
   fun currentUser(): StateFlow<User?>
   suspend fun createAccount(email: String, password: String, username: String): User?
   suspend fun login(email: String, password: String): User?
   fun logout()
}

class UserRepoImpl: UserRepo {
   private var currentUser: MutableStateFlow<User?> = MutableStateFlow(User.Unknown)
   // Switch to this line to start out the app logged out.
   // private var currentUser: MutableStateFlow<User?> = MutableStateFlow(null)

   override fun currentUser(): StateFlow<User?> {
      return currentUser
   }

   override suspend fun createAccount(email: String, password: String, username: String): User? {
      // Note: Clock would be injected in a real app, to make testing more feasible
      currentUser.tryEmit(User(id = email, email = email, name = username, accountCreation = Clock.System.now()))
      return currentUser.value
   }

   override suspend fun login(email: String, password: String): User? {
      currentUser.tryEmit(User(id = email, email = email, name = email, accountCreation = Clock.System.now()))
      return currentUser.value
   }

   override fun logout() {
      currentUser.tryEmit(null)
   }
}

