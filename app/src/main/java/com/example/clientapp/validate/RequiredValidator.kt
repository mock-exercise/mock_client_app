package com.example.clientapp.validate

class RequiredValidator(errorMessage: String) : BaseValidator(errorMessage) {

    override fun isValid(text: String): Boolean {
        return text.isNotEmpty()
    }
}