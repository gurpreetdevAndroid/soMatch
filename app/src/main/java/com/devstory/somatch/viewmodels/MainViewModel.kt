package com.devstory.somatch.viewmodels

import android.app.Application
import com.devstory.somatch.repository.preferences.UserPrefsManager

class MainViewModel(application: Application) : BaseViewModel(application) {

    fun isUserSessionSaved(): Boolean {
        return UserPrefsManager(getApplication()).isLogined
    }

}