package com.example.binder.api

import com.example.binder.model.Book
import com.example.binder.model.Giveaway
import com.example.binder.model.Match
import com.example.binder.model.UserData
import com.squareup.moshi.Json
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BinderAPI {

    @POST("api/user")
    fun registerUser(
        @Body req: RegisterUserReq
    ): Single<LoginRegUserResponse>

    @POST("api/user/login")
    fun loginUser(
        @Body req: LoginUserReq
    ): Single<LoginRegUserResponse>

    @GET("api/user")
    fun getAuthInfo(
        @Header("Authorization") token: String
    ): Single<UserData>

    @PUT("api/user")
    fun updateUser(
        @Header("Authorization") token: String,
        @Body req: UpdateUserRequest
    ): Single<UserData>

    @POST("user/book")
    fun createGiveaway(
        @Header("Authorization") token: String,
        @Body req: Giveaway
    ): Single<Giveaway>

    @GET("user/book")
    fun getUserGiveaways(
        @Header("Authorization") token: String
    ): Single<List<Giveaway>>

    @GET("api/book/{name}")
    fun getSuggestedBooks(
        @Header("Authorization") token: String,
        @Path("name") name: String
    ): Single<List<Book>>

    @DELETE("user/book/{giveAwayId}")
    fun deleteGiveaway(
        @Header("Authorization") token: String,
        @Path("giveAwayId") giveAwayId: Long
    ): Completable

    @GET("user/book/recommend/match")
    fun getMatches(
        @Header("Authorization") token: String
    ): Single<List<Match>>

    @GET("user/book/recommend")
    fun getRecommendGiveaways(
        @Header("Authorization") token: String
    ): Single<List<Giveaway>>

    @POST("user/book/recommend/{giveAwayId}")
    fun swipe(
        @Header("Authorization") token: String,
        @Path("giveAwayId") id: Long,
        @Body liked: Boolean
    ): Completable

    @POST("user/book/{bookId}")
    fun rate(
        @Header("Authorization") token: String,
        @Path("bookId") id: Long,
        @Body liked: Boolean
    ): Completable
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

data class UpdateUserRequest(
    @field:Json(name = "personal") val personal: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "year") val year: Int,
    @field:Json(name = "photo") val photo: String? = null,
)