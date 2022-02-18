package com.kv.rachtr.data.remote

import com.kv.rachtr.domain.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/user/user/UserLogin")
    suspend fun login(
        @Body params: HashMap<String, String>
    ): Response<UserModel>
}