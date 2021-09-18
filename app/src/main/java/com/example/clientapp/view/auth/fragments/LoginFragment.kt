package com.example.clientapp.view.auth.fragments

import androidx.fragment.app.activityViewModels
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentLoginBinding
import com.example.clientapp.validate.BaseValidation
import com.example.clientapp.validate.RegexValidator
import com.example.clientapp.validate.RequiredValidator
import com.example.clientapp.validate.Validation
import com.example.clientapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val mViewModel: AuthViewModel by activityViewModels()

    override fun handleTasks() {
        initListener()
        addBaseValidation()
        initView()
        mViewModel.validation.autoValidate()
    }

    private fun initView() {
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
    }

    private fun initListener() {

        binding.txtToRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            controller.navigate(action)
        }
    }

    private fun addBaseValidation() {
        mViewModel.validation.apply {
            addBaseValidation(
                BaseValidation(
                    binding.phoneInputLayout,
                    true,
                    listOf(
                        RequiredValidator(getString(R.string.error_required, "Số điện thoại")),
                        RegexValidator(
                            getString(R.string.regex_phone),
                            getString(R.string.error_regex_phone)
                        )
                    )
                )
            )
        }
    }
}