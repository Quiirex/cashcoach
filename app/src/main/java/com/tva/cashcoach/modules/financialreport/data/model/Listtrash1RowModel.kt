package com.tva.cashcoach.modules.financialreport.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class Listtrash1RowModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtCategory: String? = MyApp.getInstance().resources.getString(R.string.lbl_shopping),
    /**
     * TODO Replace with dynamic value
     */
    var txtDescription: String? = MyApp.getInstance().resources.getString(R.string.lbl_groceries),
    /**
     * TODO Replace with dynamic value
     */
    var txtAmount: String? = MyApp.getInstance().resources.getString(R.string.lbl_120),
    /**
     * TODO Replace with dynamic value
     */
    var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_10_00)

)
