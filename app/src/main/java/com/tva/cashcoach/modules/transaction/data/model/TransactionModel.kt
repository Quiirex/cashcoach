package com.tva.cashcoach.modules.transaction.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class TransactionModel(
    var category: String? = MyApp.getInstance().resources.getString(R.string.lbl_shopping),
    var description: String? = MyApp.getInstance().resources.getString(R.string.lbl_groceries),
    var amount: String? = MyApp.getInstance().resources.getString(R.string.lbl_120),
    var time: String? = MyApp.getInstance().resources.getString(R.string.lbl_10_00)


)
