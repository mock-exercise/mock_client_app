package com.example.clientapp.utils

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.example.clientapp.utils.SpinnerExtension.setSpinnerEntries


    @BindingAdapter("entries")
    fun Spinner.setEntries(entries: List<Any>?) {
        setSpinnerEntries(entries)
    }

//    @BindingAdapter("onItemSelected")
//    fun Spinner.setItemSelectedListener(itemSelectedListener: ItemSelectedListener?) {
//        setSpinnerItemSelectedListener(itemSelectedListener)
//    }
//
//    @BindingAdapter("newValue")
//    fun Spinner.setNewValue(newValue: Any?) {
//        setSpinnerValue(newValue)
//    }
