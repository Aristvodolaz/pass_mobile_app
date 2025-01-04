package com.application.x_pass.data.remote

import android.util.Log
import com.application.x_pass.utils.SPHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class HeadersInterceptor(private val spHelper: SPHelper) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer ${spHelper.getAccessToken()}")
            .header("Accept-Language", getLanguage())
            .header("Whence", "2")
            .build()
        return chain.proceed(newRequest)
    }

    private fun getLanguage(): String {
        var lng = Locale.getDefault().language
        lng = if (lng == "ru") {
            "ru-RU"
        } else if (lng == "el") {
            "el-GR"
        } else "en-US"
        Log.d("LANGEUAFE", lng)
        return lng
    }
}
