package com.example.gridify.persistence

import com.example.gridify.domain.GameStorageResult
import com.example.gridify.domain.IGameRepository
import com.example.gridify.domain.IGameStorage
import com.example.gridify.domain.ISettingsStorage
import com.example.gridify.domain.SettingStorageResult
import com.example.gridify.domain.Settings
import com.example.gridify.domain.SudokuPuzzle

class GameRepositoryImpl(
    private val gameStorage: IGameStorage,
    private val settingStorage: ISettingsStorage
) : IGameRepository {
    override suspend fun saveGame(
        elapsedTime: Long,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val getCurrentGameResult = gameStorage.getCurrentGame()) {
            is GameStorageResult.onError -> onError(getCurrentGameResult.exception)
            is GameStorageResult.onSuccess -> {
                gameStorage.updateGame(getCurrentGameResult.currentGame.copy(elapsedTime = elapsedTime))
                onSuccess(Unit)
            }
        }
    }

    override suspend fun updateGame(
        game: SudokuPuzzle,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val updateGameResult = gameStorage.updateGame(game)) {
            is GameStorageResult.onError -> onError(updateGameResult.exception)
            is GameStorageResult.onSuccess -> onSuccess(Unit)
        }

    }

    override suspend fun updateNode(
        x: Int,
        y: Int,
        elapsedTime: Long,
        color: Int,
        onSuccess: (isComplete: Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val updateNode = gameStorage.updateNode(x, y, elapsedTime, color)) {
            is GameStorageResult.onError -> onError(updateNode.exception)
            is GameStorageResult.onSuccess -> onSuccess(puzzleIsComplete(updateNode.currentGame))
        }
    }

    override suspend fun getCurrentGame(
        sudokuPuzzle: SudokuPuzzle,
        isComplete: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getSettings(
        onSuccess: (settings: Settings) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val getSettingsResult = settingStorage.getSettings()) {
            is SettingStorageResult.onError -> {
                onError(getSettingsResult.exception)
            }

            is SettingStorageResult.onSuccess -> {
                onSuccess(getSettingsResult.settings)
            }
        }
    }

    override suspend fun updateSettings(
        settings: Settings,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        settingStorage.updateSettings(settings)
        onSuccess(Unit)
    }

    private suspend fun createAndWriteNewGame(settings: Settings): GameStorageResult {
        return gameStorage.updateGame(
            SudokuPuzzle(difficulty = settings.difficulty, boundary = settings.boundary)
        )
    }

    override suspend fun createNewGame(
        settings: Settings,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val updatedSettings = settingStorage.updateSettings(setting = settings)) {
            is SettingStorageResult.onError -> {
                onError(updatedSettings.exception)
            }

            is SettingStorageResult.onSuccess -> {
                when (val newGame = createAndWriteNewGame(settings)) {
                    is GameStorageResult.onError -> onError(newGame.exception)
                    is GameStorageResult.onSuccess -> onSuccess(Unit)
                }
            }
        }
    }
}

