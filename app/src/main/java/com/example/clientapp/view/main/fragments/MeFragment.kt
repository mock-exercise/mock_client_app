package com.example.clientapp.view.main.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentMeBinding
import com.example.clientapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MeFragment : Fragment() {

    private val mViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_me,
            container,
            false
        )

        handleTasks()
        binding.viewModel =  mViewModel
        binding.lifecycleOwner = this

        return binding.root
    }


    private fun handleTasks() {

        setupData()

        initView()
        initObserve()
        initListener()
    }

    private fun initView() {
//        binding.spinner.adapter = spinnerAdapter
    }

    private fun initListener() {
        binding.cbAllow.setOnCheckedChangeListener { _, isChecked ->
            binding.btnUpdate.isEnabled = isChecked
        }

        binding.txtBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DATE)

            val datePicker = DatePickerDialog(requireContext(), { view, year, month, day ->
                binding.txtBirth.apply {
                    setText(getString(R.string.format_date,day, month, year))
                    mViewModel.setUserBirthdate(text.toString())
                }
            }, year, month, day)
            datePicker.show()
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
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

    private fun initObserve() {

//        mViewModel.liGender.observe(this,{
//            val liGenderName = it.map {item -> item.gender_name }
//
//            spinnerAdapter.clear()
//            spinnerAdapter.addAll(*liGenderName.toTypedArray())
//            spinnerAdapter.notifyDataSetChanged()
//        })

//        mViewModel.userInformation.observe(this, {
//
//        })
    }

    private fun setupData() {
        mViewModel.getUserInformation()
    }

//    override fun getFragmentBinding(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        attachToRoot: Boolean?
//    ): FragmentMeBinding = FragmentMeBinding.inflate(inflater, container, false)
}