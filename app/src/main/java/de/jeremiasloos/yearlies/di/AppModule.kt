package de.jeremiasloos.yearlies.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.jeremiasloos.yearlies.data.local.YearlyDatabase
import de.jeremiasloos.yearlies.data.preferences.DefaultPreferences
import de.jeremiasloos.yearlies.data.repository.RepositoryImpl
import de.jeremiasloos.yearlies.domain.preferences.Preferences
import de.jeremiasloos.yearlies.domain.repository.Repository
import de.jeremiasloos.yearlies.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(
        sharedPreferences: SharedPreferences
    ): Preferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideUseCases(
        repository: Repository
    ): UseCases {
        return UseCases(
            addEvent = AddEvent(repository),
            deleteEvent = DeleteEvent(repository),
            getYearlies = GetYearlies(repository)
        )
    }

    @Provides
    @Singleton
    fun provideFilterOutDigitsUseCase(): FilterOutDigits {
        return FilterOutDigits()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): YearlyDatabase {
        return Room.databaseBuilder(
            app,
            YearlyDatabase::class.java,
            "yearly_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(
        db: YearlyDatabase
    ): Repository {
        return RepositoryImpl(
            dao = db.dao
        )
    }

    @Provides
    @Singleton
    fun provideAlarmStart(
        app: Application
    ): AlarmStart {
        return AlarmStart(app)
    }

    @Provides
    @Singleton
    fun provideAlarmCancel(
        app: Application
    ): AlarmCancel {
        return AlarmCancel(app)
    }

    @Provides
    @Singleton
    fun provideAlarmCheck(
        app: Application,
        preferences: Preferences,
        useCases: UseCases
    ): AlarmCheck {
        return AlarmCheck(app, preferences, useCases)
    }
}