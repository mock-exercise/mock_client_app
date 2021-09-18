package com.example.clientapp.validate

abstract class BaseValidator(errorMessage: String) {

    val error = errorMessage

    fun validate(text: String): Boolean {
        return isValid(text)
    }

    abstract fun isValid(text: String): Boolean
}