package com.example.myapplication.domain

import com.example.myapplication.data.model.Data
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.data.network.UserRepository
import javax.inject.Inject

class GetDataUser @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): Data = repository.getAllUser()
}