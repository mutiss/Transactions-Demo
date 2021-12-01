package com.carlosblaya.transactionsdemo.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.carlosblaya.transactionsdemo.TransactionsApplication
import com.carlosblaya.transactionsdemo.data.network.ResponseHandler
import com.carlosblaya.transactionsdemo.data.network.services.TransactionsApiInterface
import com.carlosblaya.transactionsdemo.data.response.mapper.TransactionListMapper
import com.carlosblaya.transactionsdemo.domain.repository.TransactionsRepository
import com.carlosblaya.transactionsdemo.domain.usecases.GetTransactionsUseCase
import com.carlosblaya.transactionsdemo.ui.main.MainViewModel
import com.carlosblaya.transactionsdemo.util.Konsts
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule: Module = module {
    fun provideResponseHandler(): ResponseHandler {
        return ResponseHandler()
    }
    single {
        TransactionsRepository(get(), provideResponseHandler())
    }
}

val useCasesModule: Module = module {
    single {GetTransactionsUseCase (get())}
}

val mappersModule = module {
    single { TransactionListMapper() }
}

val serviceModule: Module = module {
    fun provideTransactionsUseApi(retrofit: Retrofit): TransactionsApiInterface {
        return retrofit.create(TransactionsApiInterface::class.java)
    }
    single { provideTransactionsUseApi(get()) }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    fun provideHttpClient(): OkHttpClient {
        //Caching Retrofit responses
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(TransactionsApplication.context.cacheDir, cacheSize)
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(TransactionsApplication.context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Konsts.BASE_URL_SERVICE)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}

val viewModelModule: Module = module {
    viewModel { MainViewModel(get()) }
}