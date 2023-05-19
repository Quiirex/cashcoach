package com.tva.cashcoach.modules.transaction.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class TransactionModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtToday: String? = MyApp.getInstance().resources.getString(R.string.lbl_today),
    var txtYesterday: String? = MyApp.getInstance().resources.getString(R.string.lbl_yesterday),
    /**
     * TODO Replace with dynamic value
     */
    var txtBarsOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_yesterday),

    var txtCategory: String? = MyApp.getInstance().resources.getString(R.string.lbl_shopping),
    var txtDescription: String? = MyApp.getInstance().resources.getString(R.string.lbl_groceries),
    var txtAmount: String? = MyApp.getInstance().resources.getString(R.string.lbl_120),
    var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_10_00)


)
