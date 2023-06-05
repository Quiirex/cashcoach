package com.tva.cashcoach.modules.addnewwallet.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.MyApp

data class AddNewWalletModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = MyApp.getInstance().resources.getString(R.string.lbl_add_new_wallet),
    /**
     * TODO Replace with dynamic value
     */
    var txtBalance: String? = MyApp.getInstance().resources.getString(R.string.lbl_balance),
    /**
     * TODO Replace with dynamic value
     */
    var etPriceValue: String? = null,
    /**
     * TODO Replace with dynamic value
     */
    var etInputWalletNaValue: String? = null
)
