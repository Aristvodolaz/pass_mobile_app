package com.application.x_pass.di

import android.content.Context
import android.content.SharedPreferences
import com.application.x_pass.utils.SPHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("x_pass_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSPHelper(sharedPreferences: SharedPreferences): SPHelper {
        return com.application.x_pass.utils.SPHelper(sharedPreferences)
    }
}
