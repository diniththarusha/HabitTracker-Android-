package com.example.habittracker.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private val dayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun todayKey(): String = dayFormat.format(Date())

    fun dateKey(timeMillis: Long): String = dayFormat.format(Date(timeMillis))
}


