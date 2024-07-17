package com.example.myapplication.data.network


import com.example.myapplication.data.model.Data
import com.example.myapplication.data.model.DeleteUserResponseModel
import com.example.myapplication.data.model.RequestUserModel
import com.example.myapplication.data.model.UserResponseModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface UserApi {

    @GET("data/v1/user")
    suspend fun getAllUser(
        @Header("app-id") appId: String = "63473330c1927d386ca6a3a5",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 6
    ): Data

    @POST("/data/v1/user/create")
    suspend fun createUser(
        @Body requestUser: RequestUserModel,
        @Header("app-id") appId: String = "63473330c1927d386ca6a3a5",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 6,
    ): UserResponseModel

    @DELETE("data/v1/user/{id}")
    suspend fun deleteUser(
        @Path("id") id:String,
        @Header("app-id") appId: String = "63473330c1927d386ca6a3a5",
    ): DeleteUserResponseModel
}