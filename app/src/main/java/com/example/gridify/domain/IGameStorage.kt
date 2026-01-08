package com.example.gridify.domain

interface IGameStorage {
    suspend fun updateGame(game: SudokuPuzzle):GameStorageResult
    suspend fun getCurrentGame():GameStorageResult
    suspend fun updateNode(x: Int, y: Int, elapsedTime: Long, color: Int): GameStorageResult
}

sealed class GameStorageResult{
    data class onSuccess(val currentGame: SudokuPuzzle): GameStorageResult()
    data class onError(val exception: Exception): GameStorageResult()
}