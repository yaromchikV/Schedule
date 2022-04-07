package com.yaromchikv.schedule

import android.app.Application
import com.yaromchikv.schedule.di.appModule
import com.yaromchikv.schedule.di.dataModule
import com.yaromchikv.schedule.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class ScheduleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLogger()
        setupDI()
    }

    private fun setupLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun setupDI() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ScheduleApp)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}