package com.application.x_pass

import android.app.Application
import com.application.x_pass.utils.SPHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var spHelper: SPHelper
    override fun onCreate() {
        super.onCreate()
    }
}