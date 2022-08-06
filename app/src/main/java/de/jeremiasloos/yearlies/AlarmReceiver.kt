package de.jeremiasloos.yearlies

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import de.jeremiasloos.yearlies.domain.preferences.Preferences
import de.jeremiasloos.yearlies.domain.use_case.AlarmCheck
import de.jeremiasloos.yearlies.domain.use_case.UseCases
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var alarmCheck: AlarmCheck
    @Inject lateinit var preferences: Preferences
    @Inject lateinit var useCases: UseCases

    override fun onReceive(context: Context, intent: Intent?) {
        AlarmCheck(context, preferences, useCases).alarmCheck()
        alarmCheck
    }
}
/*class AlarmReceiver @Inject constructor(
    private val alarmCheck: AlarmCheck
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        alarmCheck
    }
}*/