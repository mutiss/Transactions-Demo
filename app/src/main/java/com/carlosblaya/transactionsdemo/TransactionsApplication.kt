package com.carlosblaya.transactionsdemo

import android.app.Application
import android.content.Context
import com.carlosblaya.transactionsdemo.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TransactionsApplication: Application(){

    companion object {
        lateinit var instance: TransactionsApplication
        lateinit var context: Context
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //Koin
        startKoin {
            // declare used Android context
            androidContext(this@TransactionsApplication)
            // declare modules
            modules(retrofitModule,useCasesModule, repositoryModule, serviceModule,viewModelModule,mappersModule)
        }
    }

}