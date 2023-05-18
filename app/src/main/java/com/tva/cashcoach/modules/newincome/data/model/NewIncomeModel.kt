package com.tva.cashcoach.modules.newincome.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class NewIncomeModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = MyApp.getInstance().resources.getString(R.string.lbl_income),
    /**
     * TODO Replace with dynamic value
     */
    var txtHowmuch: String? = MyApp.getInstance().resources.getString(R.string.lbl_how_much),
    /**
     * TODO Replace with dynamic value
     */
    var txtOne: String? = MyApp.getInstance().resources.getString(R.string.lbl),
    /**
     * TODO Replace with dynamic value
     */
    var etZeroValue: String? = null,
    /**
     * TODO Replace with dynamic value
     */
    var etInputDescriptiValue: String? = null
)
