package com.mybooks.app

import android.app.Application
import com.mybooks.app.di.apiModule
import com.mybooks.app.di.repositoryModule
import com.mybooks.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyBooksApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyBooksApplication)
            modules(mutableListOf(viewModelModule, repositoryModule, apiModule))
        }
    }
}