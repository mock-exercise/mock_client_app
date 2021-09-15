package com.example.clientapp.utils

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.example.connectorlibrary.enitity.Gender
import com.google.android.material.textfield.TextInputEditText


@BindingAdapter("entries")
fun Spinner.setEntries(entries: List<Gender>?) {
    val liGenderName = entries?.map { it.gender_name }

    if (liGenderName != null) {
        val arrayAdapter = ArrayAdapter(context, R.layout.simple_spinner_item, liGenderName)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

@BindingAdapter("entries")
fun Spinner.setNewValue(entries: List<Gender>?) {
    val liGenderName = entries?.map { it.gender_name }

    if (liGenderName != null) {
        val arrayAdapter = ArrayAdapter(context, R.layout.simple_spinner_item, liGenderName)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

@BindingAdapter("init_new_value")
fun Spinner.initNewValue(position: Int) {
    if (adapter != null ) {
        setSelection(position, false)
        tag = position
    }
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
