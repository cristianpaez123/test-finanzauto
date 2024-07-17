package com.example.myapplication.iu.viewModel

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.RequestUserModel
import com.example.myapplication.data.model.UserResponseModel
import com.example.myapplication.domain.CreateUser
import com.example.myapplication.iu.view.OBLIGATORY_FIELD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViemModel @Inject constructor(
    private val createUser: CreateUser
) : ViewModel() {

    private val registerUserState: MutableLiveData<RegisterUserState> = MutableLiveData()
    fun registerUserStateUserState(): LiveData<RegisterUserState> = registerUserState

    suspend fun createUser(
        title: EditText,
        name: EditText,
        lastName: EditText,
        gender: EditText,
        email: EditText,
        dateOfBirth: EditText,
        phone: EditText
    ) {
        viewModelScope.launch {
            var newUser: RequestUserModel? = null
            if (validateFields(
                    title,
                    name,
                    lastName,
                    gender,
                    email,
                    dateOfBirth,
                    phone,
                )
            ) {
                newUser = RequestUserModel(
                    title = title.text.toString(),
                    firstName = name.text.toString(),
                    lastName = lastName.text.toString(),
                    gender = gender.text.toString(),
                    email = email.text.toString(),
                    dateOfBirth = dateOfBirth.text.toString(),
                    phone = phone.text.toString(),
                    picture = "https://randomuser.me/api/portraits/med/men/80.jpg" )
            }
            try {
                registerUserState.postValue(
                    newUser?.let {
                        createUser.invoke(
                            it
                        )
                    }?.let {
                        RegisterUserState.DataLoaded(
                            it
                        )
                    }
                )
            } catch (e: Exception) {
                registerUserState.postValue(RegisterUserState.Error("ERROR"))
            }
        }
    }

    private fun validateFields(vararg fields: EditText): Boolean {
        fields.forEach { field ->
            if (field.text.toString().trim().isEmpty()) {
                field.error = OBLIGATORY_FIELD
                return false
            }
        }
        return true
    }

    sealed class RegisterUserState {
        data class DataLoaded(val userResponseResult: UserResponseModel) : RegisterUserState()
        data class Error(val message: String) : RegisterUserState()
    }
}
