package com.tva.cashcoach.modules.signupsuccess.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class SignupSuccessModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtYoureallset: String? = App.getInstance().resources.getString(R.string.lbl_you_re_all_set)

)
