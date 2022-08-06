package de.jeremiasloos.yearlies.data.preferences

import android.content.SharedPreferences
import de.jeremiasloos.yearlies.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPref: SharedPreferences
) : Preferences {
    override fun saveNotificationsActive(active: Boolean) {
        sharedPref
            .edit()
            .putBoolean(Preferences.KEY_NOTIFICATIONS_ACTIVE, active)
            .apply()
    }

    override fun loadNotificationsActive(): Boolean {
        return sharedPref
            .getBoolean(Preferences.KEY_NOTIFICATIONS_ACTIVE, true)
    }

    override fun saveNotificationsInterval(interval: String) {
        sharedPref
            .edit()
            .putString(Preferences.KEY_NOTIFICATIONS_INTERVAL, interval)
            .apply()
    }

    override fun loadNotificationsInterval(): String {
        val interval = sharedPref
            .getString(Preferences.KEY_NOTIFICATIONS_INTERVAL, null)
        return interval ?: "off"
    }
}