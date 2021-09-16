package com.example.clientapp.view.main.chartHelper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.example.clientapp.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarker(context: Context, layoutResource: Int):  MarkerView(context, layoutResource) {

    private var tvPrice: TextView? = null

    init{
        tvPrice = findViewById(R.id.tvPrice)
    }
    @SuppressLint("SetTextI18n")
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {

        val value = entry?.y?.toLong() ?: 0
        var resText = ""

        val date = entry?.x?.toLong()?.let { Date(it) }
        //Specify the format you'd like
        val sdf = SimpleDateFormat("MM/dd", Locale.ENGLISH)
        val name = sdf.format(date)
//        if(value.toString().length > 8){
//            resText = "Val: " + value.toString().substring(0,7)
//        }
//        else{
        resText = "Ngày ${name}:  + $value"
//        }
        tvPrice?.text = "$resText người"
        super.refreshContent(entry, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {
        return MPPointF(-width / 2f, -height - 10f)
    }
}