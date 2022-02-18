package com.kv.rachtr.di

import com.kv.rachtr.common.Constants
import com.kv.rachtr.data.remote.HomeService
import com.kv.rachtr.data.remote.LoginService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    private val httpClient: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder().addInterceptor(interceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    private val retrofit_login: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val retrofit_home: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_1)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val loginService: LoginService by lazy {
        retrofit_login.create(LoginService::class.java)
    }
    val homeService: HomeService by lazy {
        retrofit_home.create(HomeService::class.java)
    }
}