package com.example.clientapp.validate

import android.util.Log

class Validation {

    private val baseValidations = arrayListOf<BaseValidation>()
    private var isValidated = true

    fun addBaseValidation(baseValidation: BaseValidation) {
        baseValidations.add(baseValidation)
    }

    fun autoValidate() {
        for ((view, auto, validators) in baseValidations) {
            view.setAutoValidate(auto, *validators.toTypedArray())
        }
    }

    fun validateAllField() {
        for ((view, _, validators) in baseValidations) {
            if (!view.validate(*validators.toTypedArray())) {
                isValidated = false
                break
            }
        }
        Log.e("TAG", "validateAllField: $isValidated", )
    }

    fun validate(): Boolean{
        validateAllField()
        return isValidated
    }
}