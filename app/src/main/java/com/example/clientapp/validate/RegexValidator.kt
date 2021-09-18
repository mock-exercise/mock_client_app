package com.example.clientapp.validate

import java.util.regex.Pattern

class RegexValidator(
    private val regex: String,
    errorMessage: String
) : BaseValidator(errorMessage) {

    override fun isValid(text: String): Boolean {
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(text).find()
    }
}