package com.tva.cashcoach.extensions

import android.util.Patterns

/**
 * Checks if a string is numeric.
 *
 * @return True if the string is numeric, false otherwise.
 */
fun String?.isNumeric(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    val numberRegex = "^[0-9]*${'$'}".toRegex()
    return numberRegex.matches(this)
}

/**
 * Checks if a string contains at least one number.
 *
 * @return True if the string contains at least one number, false otherwise.
 */
fun String?.containsNumber(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    val numberRegex = ".*[0-9].*".toRegex()
    return numberRegex.matches(this)
}

/**
 * Checks if a string is a valid password.
 *
 * A valid password must contain at least 10 characters, including at least one uppercase letter,
 * one lowercase letter, one number, and one special character.
 *
 * @return True if the string is a valid password, false otherwise.
 */
fun String?.isValidPassword(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    val passwordRegex =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{10,}$".toRegex()
    return passwordRegex.matches(this)
}

/**
 * Checks if a string is a valid email address.
 *
 * @return True if the string is a valid email address, false otherwise.
 */
fun String?.isValidEmail(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    return !Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Checks if a string contains only letters and spaces.
 *
 * @return True if the string contains only letters and spaces, false otherwise.
 */
fun String?.isText(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    val textRegex = "[a-zA-Z ]+".toRegex()
    return textRegex.matches(this)
}
