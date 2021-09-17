package com.example.clientapp.view.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.clientapp.R
import com.example.clientapp.data.repository.localsource.DataStoreManager
import com.example.clientapp.databinding.FragmentAboutMeBinding
import com.example.clientapp.databinding.LayoutOptionMeFragmentBinding
import com.example.clientapp.utils.FuncExtension.convertBase64ToBitmap
import com.example.clientapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutMeFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val mViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAboutMeBinding
    private lateinit var mLayoutButton: LayoutOptionMeFragmentBinding
    private lateinit var controller: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_about_me,
            container,
            false
        )

        handleTasks()
        return binding.root
    }

    private fun handleTasks() {
        initView()

        initListener()
        initObserve()
    }

    private fun initObserve() {
        dataStoreManager.avatarString.asLiveData().observe(viewLifecycleOwner, {
            binding.imgAvatar.setImageBitmap(it?.convertBase64ToBitmap())
        })
    }

    private fun initListener() {
        binding.lifecycleOwner = this

        mLayoutButton.btnMe.setOnClickListener(this)
        mLayoutButton.btnContact.setOnClickListener(this)
        mLayoutButton.btnFinger.setOnClickListener(this)
        mLayoutButton.btnInject.setOnClickListener(this)
        mLayoutButton.btnReservation.setOnClickListener(this)
        mLayoutButton.btnRoute.setOnClickListener(this)
    }

    private fun initView() {
        controller = findNavController()
        mLayoutButton = binding.layoutButton
        binding.viewModel = mViewModel
    }

    override fun onClick(v: View?) {
        when (v) {
            mLayoutButton.btnMe -> {
                val action = AboutMeFragmentDirections.actionAboutMeFragmentToMeFragment()
                controller.navigate(action)
            }
            mLayoutButton.btnContact, mLayoutButton.btnFinger, mLayoutButton.btnInject, mLayoutButton.btnReservation, mLayoutButton.btnRoute -> {
                val action = AboutMeFragmentDirections.actionAboutMeFragmentToDevelopmentFragment()
                controller.navigate(action)
            }
        }
    }
}