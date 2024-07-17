package com.example.myapplication.iu.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentListUserBinding
import com.example.myapplication.iu.adapter.UserAdapter
import com.example.myapplication.iu.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListUserFragment : Fragment(), UserAdapter.OnUserClickListener {

    private lateinit var binding: FragmentListUserBinding
    private val userViewModel: UserViewModel by viewModels()

    private var userAdapter: UserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initListener()
        setupObserver()
        fragmentResultListener()
    }

    private fun fragmentResultListener(){
        setFragmentResultListener("userKey") { _, bundle ->
            userViewModel.loadData()
        }
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter(this)

        with(binding.rcvUser) {
            layoutManager = GridLayoutManager(this.context, 1)
            adapter = userAdapter
        }
    }

    private fun setupObserver() {

        userViewModel.getDataUserState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserViewModel.GetDataUserState.Loading -> {
                    showLoading()
                }

                is UserViewModel.GetDataUserState.DataLoaded -> {
                    hideLoading()
                    userAdapter?.setUser(requireContext(), state.userResponseResult.data)
                }

                is UserViewModel.GetDataUserState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    hideLoading()
                }

            }
        }
    }

    private fun initListener() {
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_listUserFragment_to_RegisterUserFragment)
        }
    }

    private fun showLoading() {
        binding.progressLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressLoading.visibility = View.GONE
    }

    override fun onUserClicked(id: String) {
        userViewModel.deleteUser(id)
    }

}