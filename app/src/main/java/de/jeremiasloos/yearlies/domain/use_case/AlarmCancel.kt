package de.jeremiasloos.yearlies.domain.use_case

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import de.jeremiasloos.yearlies.AlarmReceiver
import de.jeremiasloos.yearlies.BootBroadcastReceiver
import de.jeremiasloos.yearlies.util.Const.ALARM_MANAGER_REQUEST_CODE
import javax.inject.Inject

class AlarmCancel @Inject constructor(ctx: Context) {

    val context = ctx

    fun alarmCancel() {
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
        alarmManager.cancel(alarmPendingIntent)

        // for disabling load after reboot
        val receiver = ComponentName(context, BootBroadcastReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    operator fun invoke() {
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
        alarmManager.cancel(alarmPendingIntent)

        // for disabling load after reboot
        val receiver = ComponentName(context, BootBroadcastReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}