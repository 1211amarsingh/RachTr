package com.kv.rachtr.domain.repository

import com.kv.rachtr.data.remote.LoginService
import com.kv.rachtr.domain.model.UserModel
import retrofit2.Response
import java.net.UnknownHostException


class LoginRepository(val api: LoginService) {
    suspend fun login(
        uname: String,
        password: String,
        utype: String
    ): Response<UserModel>? {
        try {
            val params: HashMap<String, String> = HashMap()
//put something inside the map, could be null
//put something inside the map, could be null
            params.put("userName", uname)
            params.put("password", password)
            params.put("userType", utype)
            val response = api.login(params)
            response.let {
                return it
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null;
    }
}