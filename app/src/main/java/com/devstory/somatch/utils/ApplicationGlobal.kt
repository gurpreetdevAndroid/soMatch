package com.devstory.somatch.utils

import android.app.Application
import com.devstory.somatch.repository.preferences.UserPrefsManager
import com.facebook.drawee.backends.pipeline.Fresco
import java.util.*


class ApplicationGlobal : Application() {

    companion object {
        var sessionId: String = ""
        var deviceLocale: String = ""
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize fresco
        Fresco.initialize(this)


        // Get device locale
        deviceLocale = Locale.getDefault().language

        // Get session id
        sessionId = UserPrefsManager(this).sessionId

    }
}
