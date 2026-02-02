package com.example.habittracker.data.model

data class Habit(
    val id: String,
    val title: String,
    val description: String = "",
    val targetPerDay: Int = 1,
    val completedDates: MutableSet<String> = mutableSetOf()
)


