package com.tva.cashcoach.modules.homescreen.data.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class HomeScreenModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtCurrentBudget: String? =
        MyApp.getInstance().resources.getString(R.string.lbl_current_budget),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = "",
    /**
     * TODO Replace with dynamic value
     */
    var txtIncome: String? = MyApp.getInstance().resources.getString(R.string.lbl_income),
    /**
     * TODO Replace with dynamic value
     */
    var txtPriceOne: String? = "",
    /**
     * TODO Replace with dynamic value
     */
    var txtExpenses: String? = MyApp.getInstance().resources.getString(R.string.lbl_expenses),
    /**
     * TODO Replace with dynamic value
     */
    var txtPriceTwo: String? = "",
    /**
     * TODO Replace with dynamic value
     */
    var txtWeek: String? = MyApp.getInstance().resources.getString(R.string.lbl_week),
    /**
     * TODO Replace with dynamic value
     */
    var txtMonth: String? = MyApp.getInstance().resources.getString(R.string.lbl_month),
    /**
     * TODO Replace with dynamic value
     */
    var txtYear: String? = MyApp.getInstance().resources.getString(R.string.lbl_year),
    /**
     * TODO Replace with dynamic value
     */
    var txtSpendFrequency: String? =
        MyApp.getInstance().resources.getString(R.string.msg_recent_transact)

)
