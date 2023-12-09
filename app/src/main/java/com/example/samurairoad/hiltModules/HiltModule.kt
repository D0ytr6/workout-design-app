package com.example.samurairoad.hiltModules

import com.example.samurairoad.repository.AuthRepository
import com.example.samurairoad.ui.auth.WorkoutApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideAuthRepository(workoutApiService: WorkoutApiService) = AuthRepository(workoutApiService)

}