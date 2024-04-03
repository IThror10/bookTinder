package com.example.binder.api

import com.example.binder.model.UserData
import com.squareup.moshi.Json
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface BinderAPI {

    @POST("api/user")
    fun registerUser(
        @Body req: RegisterUserReq
    ): Single<LoginRegUserResponse>

    @POST("api/user/login")
    fun loginUser(
        @Body req: LoginUserReq
    ): Single<LoginRegUserResponse>

}

data class RegisterUserReq(
    @field:Json(name = "login") val login: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "password") val password: String,
    @field:Json(name = "personal") val personal: String,
    @field:Json(name = "year") val year: Int,
)

data class LoginUserReq(
    @field:Json(name = "username") val username: String,
    @field:Json(name = "password") val password: String,
)

data class LoginRegUserResponse(
    @field:Json(name = "userData") val userData: UserData,
    @field:Json(name = "jsonAuth") val jsonAuth: String,
)