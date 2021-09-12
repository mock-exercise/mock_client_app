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
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val mViewModel: AuthViewModel by activityViewModels()


    override fun handleTasks() {
        initListener()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initListener() {

        binding.btnLogin.setOnClickListener {

//            val date = "20-05-2021"
//            val simpleFormat = SimpleDateFormat("dd-MM-yyyy").parse(date)!!
//
//            mViewModel.registerAccount(
//                User(
//                    name = "Nam",
//                    phone_number = "0918780192",
//                    passport_number = "123",
//                    birthday = simpleFormat,
//                    gender_id = 0,
//                )
//            )
//            dialog.startLoading()
            val phoneNumber = binding.txtPhoneNumber.text.toString()
            mViewModel.loginAccount(phoneNumber)

//            mViewModel.registerAccount()
        }

        binding.txtToRegister.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            controller.navigate(action)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
}