package com.example.clientapp.validate

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setAutoValidate(
    isAutoValidated: Boolean,
    vararg validators: BaseValidator
) {
    Log.e("TAG", "setAutoValidate: ")
    editText!!.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isAutoValidated)
                 validate(*validators)
            else
                error = null
        }

        override fun afterTextChanged(s: Editable) {}
    })
}

fun TextInputLayout.validate(vararg validators: BaseValidator): Boolean {
    Log.e("TAG", "validate: ")
    var isInputValidate = true
    val text = editText?.text.toString()
    for (validator in validators) {
        Log.e("TAG", "validate: 1111")
        if (!validator.validate(text)) {
            isErrorEnabled = true
            error = validator.error
            isInputValidate = false
            break
        } else {
            error = null
        }
    }
    return isInputValidate
}

