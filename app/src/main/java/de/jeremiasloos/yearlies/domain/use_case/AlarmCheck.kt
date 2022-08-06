package de.jeremiasloos.yearlies.domain.use_case

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.jeremiasloos.yearlies.MainActivity
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.domain.preferences.Preferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

class AlarmCheck @Inject constructor(ctx: Context, pref: Preferences, useC: UseCases) {


    val context = ctx
    val preferences = pref
    private val useCases = useC

    /*
    var listOfYearlies: List<YearlyEvent> = emptyList()


    private fun fetchYearlies(
        useCases: UseCases
    ) {
        useCases
            .getYearlies()
            .onEach { yearlies ->
                val today = LocalDate.now()
                val passedDays = yearlies.filter { it.date.dayOfYear < today.dayOfYear }
                val todayAndAheadDays = yearlies.filter { it.date.dayOfYear >= today.dayOfYear }
                val newYearliesOrder = todayAndAheadDays + passedDays
                Log.d("newOrder", newYearliesOrder.toString())
                listOfYearlies = newYearliesOrder
            }
    }
    */

    private fun fetchNextFiveYearlies(useCases: UseCases): String {
        var fiveYearlies: String
        runBlocking {
            val today = LocalDate.now()
            val rawYearlies = useCases.getYearlies().first()
            val passedDays = rawYearlies.filter { it.date.dayOfYear < today.dayOfYear }
            val todayAndAhead = rawYearlies.filter { it.date.dayOfYear >= today.dayOfYear }
            val newOrder = (todayAndAhead + passedDays).take(5)
            val fiveNames = newOrder.map { it.name }
            fiveYearlies = fiveNames.joinToString(", ")
        }
        return fiveYearlies
    }

    fun alarmCheck() {
        val intervalName = preferences.loadNotificationsInterval()
        // get list
        /*
        val firstFive = listOfYearlies.take(5)
        Log.d("firstFive", firstFive.toString())
        val firstFiveNames: List<String> = firstFive.map { it.name }
        Log.d("firstFiveNames", firstFiveNames.toString())
        //val firstFiveNamesString = firstFiveNames.joinToString(", ")
        val firstFiveNamesString = fetchNextFiveYearlies(useCases).joinToString(", ")
        */

        val notiTitle = context.getString(R.string.alarm_title)

        val notiText = fetchNextFiveYearlies(useCases)

        // stuff for notifications
        val intent = Intent(
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val manager = NotificationManagerCompat.from(context)
        val channel = "Yearlies Channel"
        val id = 89
        val builder = NotificationCompat.Builder(context, channel)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channel,
                    "Default Name",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        builder
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            //.setSmallIcon(R.drawable.ic_yearlies2)
            //.setSmallIcon(R.drawable.ic_stat_name)
            .setSmallIcon(R.drawable.ic_yearlies_y_24)
            .setContentTitle(notiTitle)
            .setContentText(notiText)
            .setContentIntent(pendingIntent)
            .setLights(0xdd337ab7.toInt(), 600, 400)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)

        when (intervalName) {
            "off" -> {}
            "daily" -> {
                manager.notify(id, builder.build())
            }
            "weekly" -> {
                val dayOfWeek = LocalDate.now().dayOfWeek
                if (dayOfWeek == DayOfWeek.SUNDAY) {
                    manager.notify(id, builder.build())
                }
            }
            "monthly" -> {
                val dayOfMonth = LocalDate.now().dayOfMonth
                if (dayOfMonth == 1) {
                    manager.notify(id, builder.build())
                }
            }
        }
    }

    /*
    operator fun invoke() {
        val intervalName = preferences.loadNotificationsInterval()
        // get list
        fetchYearlies(useCases)
        val firstFive = listOfYearlies.take(5)
        val firstFiveNames: List<String> = firstFive.map { it.name }
        val firstFiveNamesString = firstFiveNames.joinToString(", ")

        val notiTitle = "next yearlies"

        val notiText = firstFiveNamesString

        // stuff for notifications
        val intent = Intent(
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val manager = NotificationManagerCompat.from(context)
        val channel = "Yearlies Channel"
        val id = 89
        val builder = NotificationCompat.Builder(context, channel)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channel,
                    "Default Name",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        builder
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_yearlies2)
            .setContentTitle(notiTitle)
            .setContentText(notiText)
            .setContentIntent(pendingIntent)
            .setLights(0xdd337ab7.toInt(), 600, 400)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)

        when (intervalName) {
            "off" -> {}
            "daily" -> {
                manager.notify(id, builder.build())
            }
            "weekly" -> {
                val dayOfWeek = LocalDate.now().dayOfWeek
                if (dayOfWeek == DayOfWeek.SUNDAY) {
                    manager.notify(id, builder.build())
                }
            }
            "monthly" -> {
                val dayOfMonth = LocalDate.now().dayOfMonth
                if (dayOfMonth == 1) {
                    manager.notify(id, builder.build())
                }
            }
        }
    }
    */
}