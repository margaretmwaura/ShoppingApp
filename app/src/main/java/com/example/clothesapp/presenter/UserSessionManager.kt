package com.example.clothesapp.presenter

import android.content.Context
import android.content.SharedPreferences


class UserSessionManager(paramContext: Context) {
    var PRIVATE_MODE = 0
    var context: Context
    var editor: SharedPreferences.Editor
    var pref: SharedPreferences
    fun createUserLoginSession() {
        editor.putBoolean("IsUserLoggedIn", true)
        editor.commit()
    }

    val isUserLoggedIn: Boolean
        get() = pref.getBoolean("IsUserLoggedIn", false)

    init {
        context = paramContext
        pref = context.getSharedPreferences("AndroidPref", PRIVATE_MODE)
        editor = pref.edit()
    }
}
