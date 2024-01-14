package com.tonycase.navmapscompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 12/22/23.
 */
@HiltAndroidApp
class NavMapsComposeApp: Application() {
   override fun onCreate() {
      super.onCreate()

      Timber.plant(Timber.DebugTree())
   }
}
