package com.example.myapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val picture: String
) : Parcelable
