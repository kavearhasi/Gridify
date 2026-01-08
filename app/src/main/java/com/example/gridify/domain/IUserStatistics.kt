package com.example.gridify.domain

interface IUserStatistics {
    suspend fun updateStatistics(
        time: Long,
        difficulty: Difficulty,
        boundary: Int,
        onSuccess: (isRecorded: Boolean) -> Unit,
        onError: (exception: Exception) -> Unit
    )

    suspend fun getStatistics(
        onSuccess: (userStatistics: UserStatistics) -> Unit,
        onError: (exception: Exception) -> Unit
    )
}