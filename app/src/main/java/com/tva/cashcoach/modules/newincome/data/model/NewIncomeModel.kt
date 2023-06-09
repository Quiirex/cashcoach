package com.tva.cashcoach.modules.newincome.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class NewIncomeModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.lbl_income),
    /**
     * TODO Replace with dynamic value
     */
    var txtValue: String? = App.getInstance().resources.getString(R.string.lbl_how_much),
    /**
     * TODO Replace with dynamic value
     */
    var txtCurrency: String? = App.getInstance().resources.getString(R.string.lbl),
    /**
     * TODO Replace with dynamic value
     */
    var etValue: String? = null,
    /**
     * TODO Replace with dynamic value
     */
    var etDescription: String? = null,
    var etName: String? = null
)
