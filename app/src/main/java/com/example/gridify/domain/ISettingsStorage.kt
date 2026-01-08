package com.example.gridify.domain

interface ISettingsStorage {
    suspend fun getSettings(): SettingStorageResult
    suspend fun updateSettings(setting: Settings): SettingStorageResult

}
sealed class SettingStorageResult{
    data class onSuccess(val currentGame: SudokuPuzzle): SettingStorageResult()
    data class onError(val exception: Exception): SettingStorageResult()
}