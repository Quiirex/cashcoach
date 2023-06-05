package com.tva.cashcoach.modules.wallets.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class WalletsModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.lbl_wallets),
    /**
     * TODO Replace with dynamic value
     */
    var txtCurrentBudget: String? =
        App.getInstance().resources.getString(R.string.lbl_total_budget),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = App.getInstance().resources.getString(R.string.lbl_1_200)

)
