package com.example.clientapp.view.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentHistoryDeclareBinding
import com.example.clientapp.utils.Constant
import com.example.clientapp.utils.NotifyDialog
import com.example.clientapp.view.main.adapters.ShowHealthHistoryAdapters
import com.example.clientapp.viewmodel.MainViewModel

class HistoryDeclareFragment : BaseFragment<FragmentHistoryDeclareBinding>(R.layout.fragment_history_declare) {

    private val mAdapter by lazy { ShowHealthHistoryAdapters() }
    private val mViewModel: MainViewModel by activityViewModels()

    override fun handleTasks() {
        setUpData()
        initView()

        initObserve()
        initListener()
    }

    private fun initListener() {
        binding.btnNotify.setOnClickListener {
            when(mViewModel.mHealthGeneralType.value?.healthStatus){
                Constant.HealthGeneralType.SAFE ->{
                    NotifyDialog.newInstance(Constant.NotifyDialogType.NOTIFY.ordinal, requireContext().getString(R.string.notify_healthy_general_safe)).show(childFragmentManager, NotifyDialog.TAG)
                }
                Constant.HealthGeneralType.UNSAFE ->{
                    NotifyDialog.newInstance(Constant.NotifyDialogType.NOTIFY.ordinal, requireContext().getString(R.string.notify_healthy_general_unsafe)).show(childFragmentManager, NotifyDialog.TAG)
                }
                Constant.HealthGeneralType.SUGGEST ->{
                    NotifyDialog.newInstance(Constant.NotifyDialogType.NOTIFY.ordinal, requireContext().getString(R.string.notify_healthy_general_warning)).show(childFragmentManager, NotifyDialog.TAG)
                }
            }
        }
    }

    private fun setUpData() {
        mViewModel.getUserHealths()
    }

    private fun initObserve() {
        mViewModel.liSymptom.observe(viewLifecycleOwner,{
            mAdapter.getListSymptom(it)
        })

        mViewModel.liStatus.observe(viewLifecycleOwner,{
            mAdapter.getListStatus(it)
        })

        mViewModel.liUserHealths.observe(viewLifecycleOwner,{
            mAdapter.differ.submitList(it)
            mViewModel
        })
    }

    private fun initView() {
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        binding.recycleView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,true)
        }
    }
}