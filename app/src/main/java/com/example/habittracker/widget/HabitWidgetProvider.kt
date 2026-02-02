package com.example.habittracker.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.habittracker.MainActivity
import com.example.habittracker.R
import com.example.habittracker.data.PrefsStorage
import com.example.habittracker.util.DateUtils

class HabitWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        Companion.updateAppWidget(context, appWidgetManager, appWidgetId)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_REFRESH) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, HabitWidgetProvider::class.java))
            onUpdate(context, manager, ids)
        }
    }

    companion object {
        const val ACTION_REFRESH = "com.example.habittracker.widget.REFRESH"

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_habit)

            val storage = PrefsStorage(context)
            val habits = storage.getHabits()
            val today = DateUtils.todayKey()
            val total = habits.size.coerceAtLeast(1)
            val completed = habits.count { it.completedDates.contains(today) }
            val percent = (completed * 100) / total
            views.setTextViewText(R.id.text_percentage, "$percent%")

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun sendRefreshBroadcast(context: Context) {
            val intent = Intent(context, HabitWidgetProvider::class.java).apply { action = ACTION_REFRESH }
            context.sendBroadcast(intent)
        }
    }
}


