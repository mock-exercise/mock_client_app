package com.example.clientapp.view.auth.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentLoginBinding
import com.example.clientapp.utils.LoadingDialog
import com.example.clientapp.viewmodel.AuthViewModel
import com.example.connectorlibrary.enitity.User
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val mViewModel: AuthViewModel by activityViewModels()

    override fun handleTasks() {
        initListener()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initListener() {

        binding.btnLogin.setOnClickListener {

            val phoneNumber = binding.txtPhoneNumber.text.toString()

            mViewModel.loginAccount(phoneNumber)
        }

        binding.txtToRegister.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            controller.navigate(action)
        }
    }
}