package com.kv.rachtr.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kv.rachtr.domain.model.UserModel
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {
    var USER_DATASTORE = "userprefs"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

    companion object {
        val NAME = stringPreferencesKey("NAME")
        val PASSWORD = stringPreferencesKey("PASSWORD")
        val USER_TYPE = stringPreferencesKey("USER_TYPE")
    }

    suspend fun saveUser(phonebook: UserModel) {
        context.dataStore.edit {
            it[NAME] = phonebook.userName
            it[PASSWORD] = phonebook.password
            it[USER_TYPE] = phonebook.userType
        }
    }

    suspend fun getUser() = context.dataStore.data.map {
        UserModel(
            userName = it[NAME] ?: "",
            password = it[PASSWORD] ?: "",
            userType = it[USER_TYPE] ?: "",
        )
    }
}