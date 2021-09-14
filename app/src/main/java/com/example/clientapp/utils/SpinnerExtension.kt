package com.example.clientapp.utils

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner

object SpinnerExtension {

    fun Spinner.setSpinnerEntries(entries: List<Any>?) {
        if (entries != null) {
            val arrayAdapter = ArrayAdapter(context, R.layout.simple_spinner_item, entries)
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            adapter = arrayAdapter
        }
    }
}