package de.jeremiasloos.yearlies.domain.preferences

interface Preferences {
    fun saveNotificationsActive(active: Boolean)
    fun loadNotificationsActive(): Boolean


    fun saveNotificationsInterval(interval: String)
    fun loadNotificationsInterval(): String

    companion object {
        const val KEY_NOTIFICATIONS_ACTIVE = "notifications_active"
        const val KEY_NOTIFICATIONS_INTERVAL = "notifications_interval"
    }
}