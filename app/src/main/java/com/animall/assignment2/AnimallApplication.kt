package com.animall.assignment2

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnimallApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AnimallApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = AnimallApplication.applicationContext()
    }
}