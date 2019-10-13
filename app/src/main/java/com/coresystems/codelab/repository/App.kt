package com.coresystems.codelab.repository

import android.app.Application
import com.coresystems.codelab.util.SharedPreferencesManager

/**
 * Extension of the Android Application class.
 *
 * Created by coresystems on 20.09.17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.create(this)
        SharedPreferencesManager.create(this)
    }
}