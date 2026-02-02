package com.example.habittracker.data.model

data class MoodEntry(
    val id: String,
    val timestampMillis: Long,
    val emoji: String,
    val note: String = ""
)


