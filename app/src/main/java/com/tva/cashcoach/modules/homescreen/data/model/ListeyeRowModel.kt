package com.tva.cashcoach.modules.homescreen.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class ListeyeRowModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtIncome: String? = MyApp.getInstance().resources.getString(R.string.lbl_income),
    /**
     * TODO Replace with dynamic value
     */
    var txtPriceOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_1_680)

)
