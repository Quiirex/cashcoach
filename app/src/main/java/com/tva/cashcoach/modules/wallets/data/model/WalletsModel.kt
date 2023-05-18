package com.tva.cashcoach.modules.wallets.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class WalletsModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = MyApp.getInstance().resources.getString(R.string.lbl_wallets),
    /**
     * TODO Replace with dynamic value
     */
    var txtCurrentBudget: String? =
        MyApp.getInstance().resources.getString(R.string.lbl_current_budget),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = MyApp.getInstance().resources.getString(R.string.lbl_1_200)

)
