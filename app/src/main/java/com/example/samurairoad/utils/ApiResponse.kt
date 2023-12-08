package com.example.samurairoad.utils

// sealed class - limiting number of child
// abstract by default
// ковариантность - out t  ApiResponse() = ApiResponse.Success()
sealed class ApiResponse<out T> {
    object Loading: ApiResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): ApiResponse<T>()

    data class Failure(
        val errorMessage: String,
        val code: Int,
    ): ApiResponse<Nothing>()
}