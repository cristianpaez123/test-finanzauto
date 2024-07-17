package com.example.myapplication.iu.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.model.RequestUserModel
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.databinding.FragmentRegisterUserBinding
import com.example.myapplication.iu.adapter.UserAdapter
import com.example.myapplication.iu.viewModel.RegisterUserViemModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterUserFragment : Fragment(), UserAdapter.OnUserClickListener {

    private lateinit var binding: FragmentRegisterUserBinding
    private val viemModel: RegisterUserViemModel by viewModels()
    private var userAdapter: UserAdapter? = UserAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        setupObserver()


    }

    private fun setupObserver() {
        viemModel.registerUserStateUserState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegisterUserViemModel.RegisterUserState.DataLoaded -> {
                   var user = UserModel(
                        id = state.userResponseResult.id,
                        title = state.userResponseResult.title,
                        firstName = state.userResponseResult.firstName,
                        lastName = state.userResponseResult.lastName,
                        picture = state.userResponseResult.picture
                    )

                    val resultBundle = Bundle()
                    resultBundle.putParcelable("user", user)
                    setFragmentResult("requestKey", resultBundle)

                    parentFragmentManager.popBackStack()
                }

                is RegisterUserViemModel.RegisterUserState.Error -> {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initListener() {
        binding.apply {
            btnRegister.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    if (validateFields(
                            etTitle,
                            etFirstName,
                            etLastName,
                            etGender,
                            etEmail,
                            etDateOfBirth,
                            etPhone,
                        )
                    ) {

                        viemModel.createUser(
                            RequestUserModel(
                                title = etTitle.text.toString(),
                                firstName = etFirstName.text.toString(),
                                lastName = etLastName.text.toString(),
                                gender = etGender.text.toString(),
                                email = etEmail.text.toString(),
                                dateOfBirth = etDateOfBirth.text.toString(),
                                phone = etPhone.text.toString(),
                                picture = "https://randomuser.me/api/portraits/med/men/80.jpg"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun validateFields(vararg fields: EditText): Boolean {
        fields.forEach { field ->
            if (field.text.toString().trim().isEmpty()) {
                field.error = "Este campo es obligatorio"
                return false
            }
        }
        return true
    }

    override fun onUserClicked(id: String) {
        TODO("Not yet implemented")
    }
}