package com.example.clientapp.view.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentChartBinding
import com.example.clientapp.viewmodel.MainViewModel


class ChartFragment : Fragment(), View.OnClickListener {

    private val mViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentChartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_chart,
            container,
            false
        )

        handleTasks()
        binding.viewModel =  mViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun handleTasks() {

        mViewModel
    }

//    override fun getFragmentBinding(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        attachToRoot: Boolean?
//    )= FragmentChartBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when(v){
            binding.btnShowVnStatus ->{
                mViewModel.isEnableVNButton.value = false
                mViewModel.getHistoryCovidVN()
                mViewModel.getStatisticCovidVn()
            }
            binding.btnShowWorldStatus ->{
                mViewModel.isEnableVNButton.value = true
                mViewModel.getHistoryCovidWorld()
                mViewModel.getStatisticCovidWorld()
            }
        }
    }
}