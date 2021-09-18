package com.example.clientapp.validate

import android.util.Log

class Validation {

    private val baseValidations = arrayListOf<BaseValidation>()
    private var isValidated = false

    fun setIsValidate(validate: Boolean) {
        isValidated = validate
    }

    fun addBaseValidation(baseValidation: BaseValidation) {
        baseValidations.add(baseValidation)
    }

    fun autoValidate() {
        for ((view, auto, validators) in baseValidations) {
            view.setAutoValidate(auto, *validators.toTypedArray())
        }
    }

    private fun validateAllField() {
        for ((view, _, validators) in baseValidations) {
            if (!view.validate(*validators.toTypedArray())) {
                isValidated = false
                break
            } else {
                isValidated = true
            }
        }
        Log.e("TAG", "validateAllField: $isValidated")
    }

    fun validate(): Boolean {
        Log.e("TAG", "validate: 1111111111")
        validateAllField()
        return isValidated
    }
}