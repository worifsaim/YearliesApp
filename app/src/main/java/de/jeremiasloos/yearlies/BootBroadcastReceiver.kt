package de.jeremiasloos.yearlies

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import de.jeremiasloos.yearlies.domain.use_case.AlarmStart
import javax.inject.Inject

@AndroidEntryPoint
class BootBroadcastReceiver : BroadcastReceiver() {

    @Inject lateinit var alarmStart: AlarmStart

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED){
            alarmStart
            AlarmStart(context).alarmStart()
        }
    }
}
/*class BootBroadcastReceiver @Inject constructor(
    private val alarmStart: AlarmStart
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        alarmStart
    }
}*/