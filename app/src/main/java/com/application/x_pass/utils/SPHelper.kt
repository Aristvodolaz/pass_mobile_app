package com.application.x_pass.utils

import android.content.SharedPreferences

class SPHelper(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_AUTH = "is_authenticated"
        private const val KEY_USER_TYPE = "user_type"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_EVENT_ID = "key_event_id"
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun saveAuthStatus(isAuthenticated: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_AUTH, isAuthenticated).apply()
    }

    fun isUserAuthenticated(): Boolean {
        return sharedPreferences.getBoolean(KEY_AUTH, false)
    }

    fun saveUserType(userType: Int) {
        sharedPreferences.edit().putInt(KEY_USER_TYPE, userType).apply()
    }

    fun getUserType(): Int {
        return sharedPreferences.getInt(KEY_USER_TYPE, -1) // -1 как значение по умолчанию
    }
    fun saveEventId(id: Int) {
        sharedPreferences.edit().putInt(KEY_EVENT_ID, id).apply()
    }

    fun getEventId(): Int {
        return sharedPreferences.getInt(KEY_EVENT_ID, -1) // -1 как значение по умолчанию
    }
    fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, token).apply()
    }

    // Get refresh token
    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }

    // Clear refresh token
    fun clearRefreshToken() {
        sharedPreferences.edit().remove(KEY_REFRESH_TOKEN).apply()
    }
    fun clearSession() {
        sharedPreferences.edit()
            .remove(KEY_ACCESS_TOKEN)
            .remove(KEY_AUTH)
            .remove(KEY_USER_TYPE)
            .remove(KEY_REFRESH_TOKEN)
            .remove(KEY_EVENT_ID)
            .apply()
    }
}
