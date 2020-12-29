package com.devstory.somatch.repository.preferences

import android.content.Context
import android.content.SharedPreferences
import com.devstory.somatch.repository.models.PojoUserLogin
import com.devstory.somatch.repository.models.User
import com.devstory.somatch.utils.ApplicationGlobal
import com.google.gson.Gson

/**
 * Created by Mukesh on 20/7/18.
 */
class UserPrefsManager(context: Context) {

    private val mSharedPreferences: SharedPreferences
    private val mEditor: SharedPreferences.Editor

    companion object {
        // SharedPreference Keys
        private const val PREFS_FILENAME = "TestApp"
        private const val PREFS_MODE = 0
        private const val PREFS_USER_PROFILE = "user"
        private const val PREFS_IS_LOGINED = "isLogined"
        private const val PREFS_SESSION_ID = "sessionId"
    }

    init {
        mSharedPreferences = context.getSharedPreferences(PREFS_FILENAME, PREFS_MODE)
        mEditor = mSharedPreferences.edit()
    }

    fun clearUserPrefs() {
        ApplicationGlobal.sessionId = ""
        mEditor.clear()
        mEditor.apply()
    }

    val isLogined: Boolean
        get() = mSharedPreferences.getBoolean(PREFS_IS_LOGINED, false)

    fun saveUserSession(isRememberMe: Boolean = true, pojoUserLogin: PojoUserLogin) {
        ApplicationGlobal.sessionId = pojoUserLogin.session_id

        if (isRememberMe) {
            mEditor.putBoolean(PREFS_IS_LOGINED, isRememberMe)
        }
        mEditor.putString(PREFS_SESSION_ID, pojoUserLogin.session_id)
        mEditor.putString(PREFS_USER_PROFILE, Gson().toJson(pojoUserLogin.profile))
        mEditor.apply()
    }

    val loginedUser: User?
        get() = Gson().fromJson(mSharedPreferences.getString(PREFS_USER_PROFILE, ""),
                User::class.java)

    fun updateUserProfile(user: User?) {
        if (null != user) {
            mEditor.putString(PREFS_USER_PROFILE, Gson().toJson(user))
            mEditor.apply()
        }
    }

    val sessionId: String
        get() = mSharedPreferences.getString(PREFS_SESSION_ID, "") ?: ""

}