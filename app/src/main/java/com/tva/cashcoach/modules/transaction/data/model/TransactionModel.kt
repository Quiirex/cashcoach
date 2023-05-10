package com.tva.cashcoach.modules.transaction.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class TransactionModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtYesterday: String? = MyApp.getInstance().resources.getString(R.string.lbl_today),
    /**
     * TODO Replace with dynamic value
     */
    var txtBars: String? = MyApp.getInstance().resources.getString(R.string.lbl_today),
    /**
     * TODO Replace with dynamic value
     */
    var txtBarsOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_yesterday)

)
