package com.tva.cashcoach.modules.forgotpassword.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class ForgotPasswordModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.lbl_forgot_password),
    /**
     * TODO Replace with dynamic value
     */
    var txtDescription: String? = App.getInstance().resources.getString(R.string.msg_don_t_worry_e),
    /**
     * TODO Replace with dynamic value
     */
    var etInputEmailValue: String? = null
)
