package com.beinganujchaudhary.aetherread

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for AetherRead.
 * Annotated with @HiltAndroidApp to trigger Hilt's code generation.
 */
@HiltAndroidApp
class AetherReadApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Future: initialise logging, crash reporting, etc.
    }
}
