package com.example.samurairoad.hiltModules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.samurairoad.service.authApi.AuthApiWorkoutService
import com.example.samurairoad.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// create datastore, singleton obj
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

//                TODO Add Okhttp client with auth and interceptors to API build

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

//    @Singleton
//    @Provides
//    fun provideOkHttpClient(
//        authInterceptor: AuthInterceptor,
//        authAuthenticator: AuthAuthenticator,
//    ): OkHttpClient {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        return OkHttpClient.Builder()
//            .addInterceptor(authInterceptor) // add access token to headers
//            .addInterceptor(loggingInterceptor)
//            .authenticator(authAuthenticator) // refresh access token
//            .build()
//    }

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)

//    @Singleton
//    @Provides
//    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
//        AuthInterceptor(tokenManager)
//
//    @Singleton
//    @Provides
//    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
//        AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000") // local url while debug
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideAuthAPIService(retrofit: Retrofit.Builder): AuthApiWorkoutService =
        retrofit
            .build()
            .create(AuthApiWorkoutService::class.java)



}