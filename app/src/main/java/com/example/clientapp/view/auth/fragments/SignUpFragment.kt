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
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private val mViewModel: AuthViewModel by activityViewModels()
    override fun handleTasks() {
        initView()
        initListener()
        initObserve()
    }

    private fun initView() {
    }

    private fun initObserve() {

    }

    private fun initListener() {
        binding.btnRegister.setOnClickListener(this)
        binding.cbAllow.setOnClickListener(this)
        binding.txtBirth.setOnClickListener(this)

        binding.cbAllow.setOnCheckedChangeListener { _, isChecked ->
            binding.btnRegister.isEnabled = isChecked

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v) {
            binding.btnRegister -> {
                val phoneNumber = binding.txtPhoneNumber.text.toString()
                val name = binding.txtName.text.toString()

                val date = "00-00-1999"
                val simpleFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(date)!!

                mViewModel.registerAccount(
                    User(
                        name = name,
                        phone_number = phoneNumber
                    )
                )
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
                    binding.txtBirth.setText("$day-$month-$year")
                }, year, month, day)
                datePicker.show()
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean?
    ): FragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }
}