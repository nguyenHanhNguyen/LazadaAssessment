package com.lazada

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LzdApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}