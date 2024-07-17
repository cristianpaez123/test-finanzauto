package com.example.myapplication.iu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Data
import com.example.myapplication.domain.DeleteUser
import com.example.myapplication.domain.GetDataUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getDataUser: GetDataUser,
    private val deleteUserUseCase: DeleteUser,
) : ViewModel() {

    private val dataUserState: MutableLiveData<GetDataUserState> = MutableLiveData()
    fun getDataUserState(): LiveData<GetDataUserState> = dataUserState

    init {
        loadData()
    }

    fun loadData() {
        dataUserState.value = GetDataUserState.Loading
        viewModelScope.launch {
            try {
                val userData = getDataUser()
                dataUserState.value = GetDataUserState.DataLoaded(userData)
            } catch (e: Exception) {
                dataUserState.value = GetDataUserState.Error("Failed to load data")
            }
        }
    }

    fun deleteUser(id: String) {
        dataUserState.value = GetDataUserState.Loading
        viewModelScope.launch {
            try {
                deleteUserUseCase(id)
                loadData()
            } catch (e: Exception) {
                dataUserState.value = GetDataUserState.Error("Failed to delete user")
            }
        }
    }

    sealed class GetDataUserState {
        data object Loading : GetDataUserState()
        data class DataLoaded(val userResponseResult: Data) : GetDataUserState()
        data class Error(val message: String) : GetDataUserState()
    }
}
