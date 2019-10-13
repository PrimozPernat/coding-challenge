package com.coresystems.codelab.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.support.annotation.VisibleForTesting
import org.jetbrains.annotations.TestOnly

class SharedPreferencesManager : ISharedPreferencesManager {

    private object Holder {
        val INSTANCE = SharedPreferencesManager()
    }

    companion object {
        val instance: SharedPreferencesManager by lazy { Holder.INSTANCE }

        private lateinit var sharedPreferences: SharedPreferences

        fun create(context: Context) {
            sharedPreferences = context.getSharedPreferences(context.packageName, MODE_PRIVATE)
        }
    }

    override fun getValue(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    override fun setValue(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    @VisibleForTesting
    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}

interface ISharedPreferencesManager {
    fun getValue(key: String): Boolean

    fun setValue(key: String, value: Boolean)

    @VisibleForTesting
    fun clear()
}