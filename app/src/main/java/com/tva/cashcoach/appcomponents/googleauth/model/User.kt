package com.tva.cashcoach.appcomponents.googleauth.model

data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val currency: String = "",
    val language: String = "",
    val theme: String = "",
    val avatar: String = ""
)