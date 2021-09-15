package com.example.clientapp.view.main.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentHistoryDeclareBinding
import com.example.clientapp.view.main.adapters.ShowHealthHistoryAdapters
import com.example.clientapp.viewmodel.MainViewModel
import com.example.connectorlibrary.enitity.Health

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
        mViewModel.liSymptom.observe(viewLifecycleOwner,{
            Log.e("TAG", "initObserve: da cap nhat trieu chứng", )
            mAdapter.getListSymptom(it)
        })

        mViewModel.liStatus.observe(viewLifecycleOwner,{
            Log.e("TAG", "initObserve: da cap nhat status", )
            mAdapter.getListStatus(it)
        })

        mViewModel.liUserHealths.observe(viewLifecycleOwner,{
            Log.e("TAG", "initObserve: da cap nhap user healths", )
            mAdapter.differ.submitList(it)
        })
    }

    private fun initView() {
        binding.recycleView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,true)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean?
    ) = FragmentHistoryDeclareBinding.inflate(inflater, container, false)
}