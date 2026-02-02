package com.example.habittracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class HabitTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createHydrationChannel()
    }

    private fun createHydrationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = HYDRATION_CHANNEL_ID
            val name = "Hydration Reminders"
            val descriptionText = "Reminders to drink water at your chosen interval"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val HYDRATION_CHANNEL_ID = "hydration_reminders"
    }
}


