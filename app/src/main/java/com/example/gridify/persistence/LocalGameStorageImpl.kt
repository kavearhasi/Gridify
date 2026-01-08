package com.example.gridify.persistence

import com.example.gridify.domain.GameStorageResult
import com.example.gridify.domain.IGameStorage
import com.example.gridify.domain.SudokuPuzzle
import com.example.gridify.domain.getHash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

private const val FILE_NAME = "game_state.txt"

class LocalGameStorageImpl(
    fileStorageDirectory: String,
    private val pathToStorageFile: File = File(fileStorageDirectory, FILE_NAME)
) : IGameStorage {

    override suspend fun updateGame(
        game: SudokuPuzzle,
    ): GameStorageResult = withContext(Dispatchers.IO) {
        try {
            updateGameData(game)
            GameStorageResult.onSuccess(game)
        } catch (e: Exception) {
            GameStorageResult.onError(e)
        }
    }

    private fun updateGameData(game: SudokuPuzzle) {
        try {
            val fileOutputStream = FileOutputStream(pathToStorageFile)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(game)
            objectOutputStream.close()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateNode(
        x: Int,
        y: Int,
        elapsedTime: Long,
        color: Int
    ): GameStorageResult = withContext(Dispatchers.IO) {
        try {
            val game = getGame()
            game.graph[getHash(x, y)]!!.first.color = color
            game.elapsedTime = elapsedTime
            updateGameData(game)
            GameStorageResult.onSuccess(game)
        } catch (e: Exception) {
            GameStorageResult.onError(e)
        }
    }

    override suspend fun getCurrentGame(): GameStorageResult = withContext(Dispatchers.IO) {
        try {
            GameStorageResult.onSuccess(getGame())
        } catch (e: Exception) {
            GameStorageResult.onError(e)
        }
    }



    private fun getGame(): SudokuPuzzle {
        try {
            var game: SudokuPuzzle

            val fileInputStream = FileInputStream(pathToStorageFile)
            val objectInputStream = ObjectInputStream(fileInputStream)
            game = objectInputStream.readObject() as SudokuPuzzle
            objectInputStream.close()

            return (game)
        } catch (e: FileNotFoundException) {
            throw e
        }
    }
}

