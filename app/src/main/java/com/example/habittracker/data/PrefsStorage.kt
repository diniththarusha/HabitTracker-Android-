package com.example.habittracker.data

import android.content.Context
import android.content.SharedPreferences
import com.example.habittracker.data.model.Habit
import com.example.habittracker.data.model.MoodEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsStorage(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("habit_tracker_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getHabits(): MutableList<Habit> {
        val json = prefs.getString(KEY_HABITS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Habit>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }

    fun saveHabits(habits: List<Habit>) {
        val json = gson.toJson(habits)
        prefs.edit().putString(KEY_HABITS, json).apply()
    }

    fun getMoods(): MutableList<MoodEntry> {
        val json = prefs.getString(KEY_MOODS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<MoodEntry>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }

    fun saveMoods(moods: List<MoodEntry>) {
        val json = gson.toJson(moods)
        prefs.edit().putString(KEY_MOODS, json).apply()
    }

    fun getHydrationIntervalMinutes(defaultMinutes: Int = 120): Int {
        return prefs.getInt(KEY_HYDRATION_MINUTES, defaultMinutes)
    }

    fun setHydrationIntervalMinutes(minutes: Int) {
        prefs.edit().putInt(KEY_HYDRATION_MINUTES, minutes).apply()
    }

    companion object {
        private const val KEY_HABITS = "habits_json"
        private const val KEY_MOODS = "moods_json"
        private const val KEY_HYDRATION_MINUTES = "hydration_interval_minutes"
    }
}


