package com.example.myapplication.data.network

import com.example.myapplication.data.model.Data
import com.example.myapplication.data.model.DeleteUserResponseModel
import com.example.myapplication.data.model.RequestUserModel
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.model.UserResponseModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: UserService) {

    suspend fun getAllUser(): Data = api.getUser()

    suspend fun createUser(requestUserModel: RequestUserModel): UserResponseModel = api.createUser(requestUserModel)

    suspend fun deleteUser(id:String): DeleteUserResponseModel = api.deleteUser(id)

}