package com.example.clientapp.view.auth.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.activityViewModels
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentSignUpBinding
import com.example.clientapp.validate.*
import com.example.clientapp.viewmodel.AuthViewModel
import com.example.connectorlibrary.enitity.User
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up),
    View.OnClickListener {

    private val mViewModel: AuthViewModel by activityViewModels()

    override fun handleTasks() {
        initListener()
        initView()
        addBaseValidation()
        mViewModel.validation.autoValidate()
    }

    private fun initView() {
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
    }

    private fun initListener() {
        binding.btnContinue.setOnClickListener(this)
        binding.cbAllow.setOnClickListener(this)
        binding.txtBirth.setOnClickListener(this)

        binding.cbAllow.setOnCheckedChangeListener { _, isChecked ->
            binding.btnContinue.isEnabled = isChecked
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v) {
            binding.btnContinue -> {
                if (mViewModel.validation.validate()) {
                    val action =
                        SignUpFragmentDirections.actionSignUpFragmentToExtraSignUpFragment()
                    controller.navigate(action)
                    mViewModel.validation.setIsValidate(false)
                }
            }
            binding.txtToLogin -> {
                controller.popBackStack()
            }
            binding.txtBirth -> {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DATE)

                val datePicker = DatePickerDialog(requireContext(), { view, year, month, day ->
                    binding.txtBirth.apply {
                        setText("$day-$month-$year")
                        mViewModel.setUserSignUpBirthdate(text.toString())
                    }
                }, year, month, day)
                datePicker.show()
            }
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
            addBaseValidation(
                BaseValidation(
                    binding.nameInputLayout,
                    true,
                    listOf(
                        RequiredValidator(
                            getString(R.string.error_required, "Họ và tên"),
                        ),
                        LengthValidator(
                            10, 20, getString(R.string.error_length_name)
                        )
                    )
                )
            )
        }
    }
}