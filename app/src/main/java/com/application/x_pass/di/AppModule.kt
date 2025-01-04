package com.application.x_pass.di

import android.content.Context
import androidx.room.Room
import com.application.x_pass.data.local.AppDatabase
import com.application.x_pass.data.remote.ApiService
import com.application.x_pass.data.remote.HeadersInterceptor
import com.application.x_pass.data.repository.AuthRepository
import com.application.x_pass.data.repository.EventRepository
import com.application.x_pass.data.repository.HistoryRepository
import com.application.x_pass.data.usecase.AuthenticateUserUseCase
import com.application.x_pass.data.usecase.InspectorHistoryUseCase
import com.application.x_pass.data.usecase.TokenRefreshUseCase
import com.application.x_pass.utils.Const
import com.application.x_pass.utils.SPHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNetworkUserRepository(apiService: ApiService): AuthRepository {
        return AuthRepository(apiService)
    }

    @Provides
    fun provideAuthenticateUserUseCase(
        networkUserRepository: AuthRepository
    ): AuthenticateUserUseCase {
        return AuthenticateUserUseCase(networkUserRepository)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log request and response body
        }
    }
    @Provides
    @Singleton
    fun provideTokenRefreshUseCase(
        authRepository: EventRepository,
        spHelper: SPHelper
    ): TokenRefreshUseCase {
        return TokenRefreshUseCase(authRepository, spHelper)
    }

    @Provides
    @Singleton
    fun provideInspectorHistoryUseCase(
        historyRepository: HistoryRepository,
        tokenRefreshUseCase: TokenRefreshUseCase,
        spHelper: SPHelper
    ): InspectorHistoryUseCase {
        return InspectorHistoryUseCase(historyRepository, tokenRefreshUseCase, spHelper)
    }
    @Provides
    @Singleton
    fun provideHeadersInterceptor(spHelper: SPHelper): HeadersInterceptor {
        return HeadersInterceptor(spHelper)
    }
    // Provides dependency for OkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headersInterceptor: HeadersInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headersInterceptor)  // Custom headers interceptor
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // Provides dependency for Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.URL)
            .client(okHttpClient)  // Pass OkHttpClient to Retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provides dependency for ApiService
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    // Provides dependency for Room Database
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"  // Database name
        ).fallbackToDestructiveMigration() // Migration strategy
            .build()
    }
}
