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
import com.example.clientapp.viewmodel.AuthViewModel
import com.example.connectorlibrary.enitity.User
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up), View.OnClickListener{

    private val mViewModel: AuthViewModel by activityViewModels()
    override fun handleTasks() {
        initListener()
        initView()
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
                val action = SignUpFragmentDirections.actionSignUpFragmentToExtraSignUpFragment()
                controller.navigate(action)
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
}