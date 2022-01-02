package com.segunfrancis.materialpicker

import androidx.annotation.IntRange

fun timeConverter(@IntRange(from = 0, to = 23) hour: Int, @IntRange(from = 0, to = 60) minute: Int): String {
    var am = true
    val convertedHour: String = when {
        hour > 12 -> {
            am = false
            "${hour - 12}"
        }
        hour == 12 -> {
            am = false
            hour.toString()
        }
        else -> {
            am = true
            hour.toString()
        }
    }
    val convertedMinute: String = if (minute < 10) "0$minute" else minute.toString()
    val timeOfDay: String = if (am) "am" else "pm"
    return convertedHour.plus(":").plus(convertedMinute).plus(" ").plus(timeOfDay)
}
