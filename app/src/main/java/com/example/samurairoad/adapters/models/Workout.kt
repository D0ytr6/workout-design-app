package com.example.samurairoad.adapters.models

data class Workout (
    val title: String,
    val description: String,
    val color: Int,
    val exercises_list: List<Exercise>,
    val workoutID: Long,
)