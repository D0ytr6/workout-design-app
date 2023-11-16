package com.example.samurairoad.adapters.models

import android.graphics.Bitmap

data class FullExercise(

    val title: String,

    val description: String,

    var SetsCount: Int,

    var RepsCount: Int,

    var weightValue: Int,

    var exercisePhoto: Bitmap,

)
