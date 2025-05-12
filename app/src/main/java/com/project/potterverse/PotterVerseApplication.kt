package com.project.potterverse

import android.app.Application
import com.project.potterverse.di.databaseModule
import com.project.potterverse.di.datasourceModule
import com.project.potterverse.di.networkModule
import com.project.potterverse.di.repositoryModule
import com.project.potterverse.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PotterVerseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                databaseModule,
                networkModule,
                datasourceModule,
                repositoryModule,
                viewModelModule
            )
            androidContext(this@PotterVerseApplication)
        }

    }

}