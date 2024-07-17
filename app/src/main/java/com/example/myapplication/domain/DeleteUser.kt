package com.example.myapplication.domain

import com.example.myapplication.data.model.DeleteUserResponseModel
import com.example.myapplication.data.network.UserRepository
import javax.inject.Inject

class DeleteUser @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(id:String):DeleteUserResponseModel = repository.deleteUser(id)
}