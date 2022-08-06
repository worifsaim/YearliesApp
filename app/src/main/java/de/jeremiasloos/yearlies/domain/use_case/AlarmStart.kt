package de.jeremiasloos.yearlies.domain.use_case

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import de.jeremiasloos.yearlies.AlarmReceiver
import de.jeremiasloos.yearlies.BootBroadcastReceiver
import de.jeremiasloos.yearlies.YearliesApp
import de.jeremiasloos.yearlies.util.Const.ALARM_MANAGER_REQUEST_CODE
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class AlarmStart @Inject constructor(ctx: Context) {

    val context = ctx

    private fun startAt(): Long {
        val currentTime: Long = System.currentTimeMillis()
        val currentHour: Long =
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toLong() * 60 * 60 * 1000
        val startingHour: Long = 17 * 60 * 60 * 1000
        val twentyFourHours: Long = 24 * 60 * 60 * 1000
        val offsetMinutes: Long = 5 * 60 * 1000
        val startAt: Long = if (startingHour >= currentHour) {
            currentTime + startingHour - currentHour
        } else {
            currentTime + startingHour - currentHour + twentyFourHours
        }
        return startAt + offsetMinutes
    }

    fun alarmStart() {
        //val testStart = System.currentTimeMillis() + 3000
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmPendingIntent by lazy {
            val intent = Intent(context, AlarmReceiver::class.java)
            PendingIntent.getBroadcast(
                context,
                ALARM_MANAGER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            startAt(),
            //testStart,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )

        // for enabling load after reboot
        val receiver = ComponentName(context, BootBroadcastReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
/*
    operator fun invoke()  {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmPendingIntent by lazy {
            val intent = Intent(context, AlarmReceiver::class.java)
            PendingIntent.getBroadcast(
                context,
                ALARM_MANAGER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            startAt(),
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )

        // for enabling load after reboot
        val receiver = ComponentName(context, BootBroadcastReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

 */
}