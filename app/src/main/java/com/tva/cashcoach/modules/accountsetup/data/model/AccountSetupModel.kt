package com.tva.cashcoach.modules.accountsetup.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class AccountSetupModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtLetssetupyo: String? =
        MyApp.getInstance().resources.getString(R.string.msg_let_s_set_up_yo),
    /**
     * TODO Replace with dynamic value
     */
    var txtDontworryyo: String? = MyApp.getInstance().resources.getString(R.string.msg_don_t_worry_yo)

)
