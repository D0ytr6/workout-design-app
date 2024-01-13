package com.example.samurairoad.hiltModules

import com.example.samurairoad.repository.AuthRepository
import com.example.samurairoad.service.authApi.AuthApiWorkoutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideAuthRepository(authApiService: AuthApiWorkoutService) = AuthRepository(authApiService)

}