package com.example.clientapp.view.main.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentHistoryDeclareBinding
import com.example.clientapp.view.main.adapters.ShowHealthHistoryAdapters
import com.example.clientapp.viewmodel.MainViewModel

class HistoryDeclareFragment : BaseFragment<FragmentHistoryDeclareBinding>() {

    private val mAdapter by lazy { ShowHealthHistoryAdapters() }
    private val mViewModel: MainViewModel by activityViewModels()


    override fun handleTasks() {
        setUpData()
        initView()

        initObserve()
    }

    private fun setUpData() {
        mViewModel.getUserHealths()
    }

    private fun initObserve() {
        mViewModel.liUserHealths.observe(viewLifecycleOwner,{
            mAdapter.differ.submitList(it)
        })
    }

    private fun initView() {
        binding.recycleView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean?
    ) = FragmentHistoryDeclareBinding.inflate(inflater, container, false)
}