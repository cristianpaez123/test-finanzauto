package com.example.myapplication.domain

import com.example.myapplication.data.model.Data
import com.example.myapplication.data.model.RequestUserModel
import com.example.myapplication.data.model.UserResponseModel
import com.example.myapplication.data.network.UserRepository
import javax.inject.Inject

class CreateUser @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(requestUserModel: RequestUserModel): UserResponseModel = repository.createUser(requestUserModel)

}