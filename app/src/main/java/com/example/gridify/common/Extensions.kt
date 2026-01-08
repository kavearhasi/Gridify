package com.example.gridify.common

import android.app.Activity
import android.widget.Toast
import com.example.gridify.R
import com.example.gridify.domain.Difficulty

internal fun Activity.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

internal fun Long.toTime(): String {
    if (this >= 3600) return "+59:59"
    var minutes: String = ((this % 3600) / 60).toString()
    if (minutes.length <= 1) minutes = "0$minutes"
    var seconds: String = (this % 60).toString()
    if (seconds.length <= 1) seconds = "0$seconds"
    return String.format("$minutes:$seconds")
}

internal val Difficulty.toLocalizedResources: Int
    get() {
        return when (this) {
            Difficulty.EASY -> R.string.easy
            Difficulty.MEDIUM -> R.string.medium
            Difficulty.HARD -> R.string.hard
        }
    }