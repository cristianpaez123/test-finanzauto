package com.example.myapplication.iu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Data
import com.example.myapplication.data.model.DeleteUserResponseModel
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
        dataUserState.postValue(GetDataUserState.Loading)
        viewModelScope.launch {
            try {
                dataUserState.postValue(GetDataUserState.DataLoaded(getDataUser()))
            } catch (e: Exception) {
                dataUserState.postValue(GetDataUserState.Error("ERROR"))
            }
        }
    }

    fun deleteUser(id: String) {
        dataUserState.postValue(GetDataUserState.Loading)
        viewModelScope.launch {
            try {
                dataUserState.postValue(GetDataUserState.DeleteUser(deleteUserUseCase(id)))
            } catch (e: Exception) {
                dataUserState.postValue(GetDataUserState.Error("ERROR"))
            }
        }
    }

    sealed class GetDataUserState {
        data object Loading : GetDataUserState()
        data class DataLoaded(val userResponseResult: Data) : GetDataUserState()
        data class DeleteUser(val idDelete:DeleteUserResponseModel) : GetDataUserState()
        data class Error(val message: String) : GetDataUserState()
    }
}
