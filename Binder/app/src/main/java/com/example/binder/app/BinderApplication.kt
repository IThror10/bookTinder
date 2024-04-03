package com.example.binder.app

import android.app.Application
import com.example.binder.api.BinderAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class BinderApplication : Application() {

    lateinit var appRetrofit: Retrofit
    lateinit var binderApi: BinderAPI

    companion object {
        private val API = "http://81.94.156.182:8080/"
        lateinit var instance: BinderApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appRetrofit = Retrofit.Builder()
            .baseUrl(API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


        binderApi = appRetrofit.create(BinderAPI::class.java)
    }

}