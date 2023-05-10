package com.tva.cashcoach.modules.financialreportlinechart.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class Listtrash1RowModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtShopping: String? = MyApp.getInstance().resources.getString(R.string.lbl_shopping),
    /**
     * TODO Replace with dynamic value
     */
    var txtBuyanAvocado: String? = MyApp.getInstance().resources.getString(R.string.lbl_groceries),
    /**
     * TODO Replace with dynamic value
     */
    var txtPriceOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_120),
    /**
     * TODO Replace with dynamic value
     */
    var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_10_00)

)
