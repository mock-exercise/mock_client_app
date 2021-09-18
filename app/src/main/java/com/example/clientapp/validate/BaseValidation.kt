package com.example.clientapp.validate

import com.google.android.material.textfield.TextInputLayout

data class BaseValidation(
    var textInputLayout: TextInputLayout,
    var autoValidate : Boolean,
    var validators: List<BaseValidator>
)