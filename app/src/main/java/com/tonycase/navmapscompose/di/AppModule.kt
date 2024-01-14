package com.tonycase.navmapscompose.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import com.tonycase.navmapscompose.repositories.RaceRepo
import com.tonycase.navmapscompose.repositories.RaceRepoImpl
import com.tonycase.navmapscompose.repositories.UserRepo
import com.tonycase.navmapscompose.repositories.UserRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides Singleton objects to be injected at the App layer.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Provides
   @Singleton
   fun resources(application: Application): Resources {
      return application.resources
   }

   @Provides
   @Singleton
   internal fun userRepo(): UserRepo = UserRepoImpl()

   @Provides
   @Singleton
   internal fun raceRepo(): RaceRepo = RaceRepoImpl()
}
