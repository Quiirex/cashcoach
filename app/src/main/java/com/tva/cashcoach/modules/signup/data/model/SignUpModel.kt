package com.tva.cashcoach.modules.signup.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class SignUpModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.lbl_sign_up),
    /**
     * TODO Replace with dynamic value
     */
    var txtOr: String? = App.getInstance().resources.getString(R.string.lbl_or),
    /**
     * TODO Replace with dynamic value
     */
    var txtSignUpwithGo: String? =
        App.getInstance().resources.getString(R.string.msg_sign_up_with_go),
    /**
     * TODO Replace with dynamic value
     */
    var txtConfirmation: String? =
        App.getInstance().resources.getString(R.string.msg_already_have_an),
    /**
     * TODO Replace with dynamic value
     */
    var etInputNameValue: String? = null,
    /**
     * TODO Replace with dynamic value
     */
    var etInputSurnameValue: String? = null,
    /**
     * TODO Replace with dynamic value
     */
    var etInputEmailValue: String? = null,
    /**
     * TODO Replace with dynamic value
     */
    var etInputPasswordValue: String? = null,
    /**
     * TODO Replace with dynamic value
     */
    var etInputPasswordOneValue: String? = null
)
