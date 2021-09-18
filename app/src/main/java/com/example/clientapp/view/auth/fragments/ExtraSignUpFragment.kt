package com.example.clientapp.view.auth.fragments

import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentExtraSignUpBinding
import com.example.clientapp.validate.*
import com.example.clientapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtraSignUpFragment : BaseFragment<FragmentExtraSignUpBinding>(R.layout.fragment_extra_sign_up) {

    private val mViewModel: AuthViewModel by activityViewModels()

    override fun handleTasks() {
        initView()

        initListener()

        addBaseValidation()

        mViewModel.validation.autoValidate()

    }

    private fun initListener() {
        binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mViewModel.setUserGender(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }
    }

    private fun initView() {
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
    }

    private fun addBaseValidation() {
        mViewModel.validation.apply {
            addBaseValidation(
                BaseValidation(
                    binding.emailInputLayout,
                    true,
                    listOf(
                        RequiredValidator(
                            getString(R.string.error_required, "Email "),
                        ),
                        RegexValidator(
                            getString(R.string.regex_email),
                            getString(R.string.error_regex_email)
                        )
                    )
                )
            )
            addBaseValidation(
                BaseValidation(
                    binding.addressInputLayout,
                    true,
                    listOf(
                        RequiredValidator(
                            getString(R.string.error_required, "Địa chỉ "),
                        ),
                        LengthValidator(
                            10, 20, getString(R.string.error_length_address)
                        )
                    )
                )
            )
        }
    }
}