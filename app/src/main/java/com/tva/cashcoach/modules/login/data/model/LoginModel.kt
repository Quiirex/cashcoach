package com.tva.cashcoach.modules.login.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class LoginModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.lbl_login),
    /**
     * TODO Replace with dynamic value
     */
    var txtOr: String? = App.getInstance().resources.getString(R.string.lbl_or),
    /**
     * TODO Replace with dynamic value
     */
    var txtSignUpwithGo: String? =
        App.getInstance().resources.getString(R.string.msg_continue_with_google),
    /**
     * TODO Replace with dynamic value
     */
    var txtForgotPassword: String? =
        App.getInstance().resources.getString(R.string.msg_forgot_password),
    /**
     * TODO Replace with dynamic value
     */
    var txtConfirmation: String? =
        App.getInstance().resources.getString(R.string.msg_don_t_have_an_a),
    /**
     * TODO Replace with dynamic value
     */
    var etInputEmailValue: String? = null,
    /**
     * TODO Replace with dynamic value
     */
    var etInputPasswordValue: String? = null
)
