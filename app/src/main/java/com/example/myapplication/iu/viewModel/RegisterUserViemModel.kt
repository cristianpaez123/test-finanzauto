package com.example.myapplication.iu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.RequestUserModel
import com.example.myapplication.data.model.UserResponseModel
import com.example.myapplication.domain.CreateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViemModel @Inject constructor(
    private val createUser: CreateUser
) : ViewModel() {

    private val registerUserState: MutableLiveData<RegisterUserState> = MutableLiveData()
    fun registerUserStateUserState(): LiveData<RegisterUserState> = registerUserState

    suspend fun createUser(requestUserModel: RequestUserModel) {
        viewModelScope.launch {
            try {
                registerUserState.postValue(
                    RegisterUserState.DataLoaded(
                        createUser.invoke(
                            requestUserModel
                        )
                    )
                )
            } catch (e: Exception) {
                registerUserState.postValue(RegisterUserState.Error("ERROR"))
            }
        }
    }

    sealed class RegisterUserState {
        data class DataLoaded(val userResponseResult: UserResponseModel) : RegisterUserState()
        data class Error(val message: String) : RegisterUserState()
    }
}
