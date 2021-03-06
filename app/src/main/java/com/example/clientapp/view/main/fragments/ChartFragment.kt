package com.example.clientapp.view.main.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentChartBinding
import com.example.clientapp.utils.Constant
import com.example.clientapp.view.main.chartHelper.CustomMarker
import com.example.clientapp.viewmodel.MainViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChartFragment : BaseFragment<FragmentChartBinding>(R.layout.fragment_chart), View.OnClickListener {

    private val mViewModel: MainViewModel by activityViewModels()
    private lateinit var mLineChart: LineChart

    private val myFormatter = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val date = Date(value.toLong())
            val sdf = SimpleDateFormat("MM/dd", Locale.ENGLISH)
            return sdf.format(date)
        }
    }


    override fun handleTasks() {

        setUpData()

        intiView()
        initBinding()
        initObserve()
        initListener()
    }

    private fun initListener() {
        binding.btnShowVnStatus.setOnClickListener(this)
        binding.btnShowWorldStatus.setOnClickListener(this)
    }

    private fun intiView() {
        mLineChart = binding.lineChart

        val xAxis = mLineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = myFormatter

        mLineChart.description.isEnabled = true
        mLineChart.description.text = "People"

        mLineChart.xAxis.labelRotationAngle = 30f
        mLineChart.axisRight.isEnabled = false
        mLineChart.setTouchEnabled(true)
        mLineChart.setPinchZoom(true)
        mLineChart.description.text = "Days"
        mLineChart.setNoDataText("Bi???u ????? ??ang t???i d??? li???u!")
        mLineChart.animateX(18000, Easing.EaseInExpo)

        val markerView = CustomMarker(requireContext(), R.layout.marker_view)
        mLineChart.marker = markerView
    }

    private fun setUpData() {

        mViewModel.getHistoryCovidVN()
    }

    private fun initBinding() {

        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
    }

    @SuppressLint("ResourceAsColor")
    private fun initObserve() {

        mViewModel.mLiHistoryCovid.observe(viewLifecycleOwner, { dataCovid ->
            var nameLine = ""
            var lineColor = R.color.purple_200
            val dataSets = mutableListOf<ILineDataSet>()

            dataCovid.forEach { caseNumbers ->

                val entries = ArrayList<Entry>()

                caseNumbers.listPeopleInDay.forEach { peopleData ->
                    entries.add(
                        Entry(
                            peopleData.day.toFloat(),
                            peopleData.people.toFloat()
                        )
                    )
                }

                when (caseNumbers.status) {
                    Constant.StatusCovid.CASE.numberIndex -> {
                        nameLine = "Nhi???m b???nh m???i"
                        lineColor = Color.rgb(198, 252, 84)
                    }
                    Constant.StatusCovid.DEATH.numberIndex -> {
                        nameLine = "T??? vong"
                        lineColor = Color.rgb(170, 6, 1)
                    }
                    Constant.StatusCovid.RECOVERED.numberIndex -> {
                        nameLine = "H???i ph???c"
                        lineColor = Color.rgb(187, 134, 252)
                    }
                }
//                lineColor = Color.rgb(244, 117, 117)
                val dataSet = LineDataSet(entries, nameLine)
                dataSet.setDrawValues(false)
                dataSet.color = lineColor
                dataSet.setCircleColor(lineColor)
                dataSet.circleHoleColor = lineColor
                dataSet.lineWidth = 3f
                dataSet.highlightLineWidth = 3f
                dataSets.add(dataSet)
            }
            val lineData = LineData(dataSets)
            mLineChart.data = lineData
            mLineChart.invalidate()
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnShowVnStatus -> {
                mViewModel.isEnableVNButton.value = false
                mViewModel.getHistoryCovidVN()
            }
            binding.btnShowWorldStatus -> {
                mViewModel.isEnableVNButton.value = true

                mViewModel.getHistoryCovidWorld()
            }
        }
    }
}