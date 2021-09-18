package com.example.clientapp.view.main.fragments

import android.app.DatePickerDialog
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.data.repository.localsource.DataStoreManager
import com.example.clientapp.databinding.FragmentMeBinding
import com.example.clientapp.utils.FuncExtension.convertBase64ToBitmap
import com.example.clientapp.view.main.dialogs.PickPictureDialog
import com.example.clientapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MeFragment : BaseFragment<FragmentMeBinding>(R.layout.fragment_me) {

    private val mViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var dataStoreManager: DataStoreManager


    override fun handleTasks() {

        setupData()

        initView()
        initObserve()
        initListener()
    }

    private fun initView() {
        binding.viewModel =  mViewModel
        binding.lifecycleOwner = this
    }

    private fun initListener() {

        binding.imgAvatar.setOnClickListener {
            PickPictureDialog.newInstance().show(childFragmentManager, PickPictureDialog.TAG)
        }

        binding.cbAllow.setOnCheckedChangeListener { _, isChecked ->
            binding.btnUpdate.isEnabled = isChecked
        }

        binding.txtBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DATE)

            val datePicker = DatePickerDialog(requireContext(), { _, year, month, day ->
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

        dataStoreManager.avatarString.asLiveData().observe(viewLifecycleOwner, {
            binding.imgAvatar.setImageBitmap(it?.convertBase64ToBitmap())
        })
    }

    private fun setupData() {
        mViewModel.getUserInformationFromServer()
    }
}