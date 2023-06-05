package com.tva.cashcoach.modules.newexpense.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class NewExpenseModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.lbl_expense),
    /**
     * TODO Replace with dynamic value
     */
    var txtHowmuch: String? = App.getInstance().resources.getString(R.string.lbl_how_much),
    /**
     * TODO Replace with dynamic value
     */
    var txtTwo: String? = App.getInstance().resources.getString(R.string.lbl),
    /**
     * TODO Replace with dynamic value
     */
    var txtWriteadescrip: String? =
        App.getInstance().resources.getString(R.string.msg_write_a_descrip),
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
