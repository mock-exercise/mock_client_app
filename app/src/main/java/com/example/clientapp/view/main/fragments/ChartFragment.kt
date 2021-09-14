package com.example.clientapp.view.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentChartBinding
import com.example.clientapp.viewmodel.MainViewModel


class ChartFragment : BaseFragment<FragmentChartBinding>() {

    val mViewModel: MainViewModel by activityViewModels()

    override fun handleTasks() {
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean?
    )= FragmentChartBinding.inflate(inflater, container, false)
}