package com.tva.cashcoach.extensions

import android.util.Patterns

fun String?.isNumeric(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    val numberRegex = "^[0-9]*${'$'}".toRegex()
    return numberRegex.matches(this)
}

fun String?.containsNumber(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    val numberRegex = ".*[0-9].*".toRegex()
    return numberRegex.matches(this)
}

/**
 * - at least 1 uppercase letter
 * - at least 1 lowercase letter
 * - at least 1 digit
 * - at least 1 special character [@#$%^&+=]
 * - length of at least 10 characters
 * - no white space allowed
 */
fun String?.isValidPassword(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    val passwordRegex =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{10,}$".toRegex()
    return passwordRegex.matches(this)
}

fun String?.isValidEmail(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    return !Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isText(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    val textRegex = "[a-zA-Z ]+".toRegex()
    return textRegex.matches(this)
}
