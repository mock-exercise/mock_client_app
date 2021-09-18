package com.example.clientapp.validate

class LengthValidator(
    private val minimumLength: Int = LENGTH_ZERO,
    private val maximumLength: Int = LENGTH_INDEFINITE,
    errorMessage: String
) : BaseValidator(errorMessage) {

    val minlenght: Int
        get() {
            return minimumLength
        }
    val maxLenght: Int
        get() {
            return maximumLength
        }

    override fun isValid(text: String): Boolean {
        val lenght = text.length
        return if (maxLenght == LENGTH_INDEFINITE) lenght >= minlenght
        else lenght in minlenght..maxLenght
    }

    companion object {

        const val LENGTH_INDEFINITE = -1

        const val LENGTH_ZERO = 0

    }
}