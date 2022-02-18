package com.kv.rachtr

import android.app.Application
import com.kv.rachtr.data.pref.DataStoreManager
import com.kv.rachtr.di.ApiClient
import com.kv.rachtr.domain.repository.HomeRepository
import com.kv.rachtr.domain.repository.LoginRepository

class MyApplication : Application() {
    val dataStoreManager: DataStoreManager by lazy {
        DataStoreManager(applicationContext)
    }
    val loginRepository by lazy { LoginRepository(ApiClient.loginService) }
    val homeRepository by lazy { HomeRepository(ApiClient.homeService) }
}