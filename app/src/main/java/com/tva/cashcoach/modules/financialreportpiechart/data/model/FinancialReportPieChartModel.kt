package com.tva.cashcoach.modules.financialreportpiechart.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class FinancialReportPieChartModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = MyApp.getInstance().resources.getString(R.string.msg_financial_repor),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = MyApp.getInstance().resources.getString(R.string.lbl_136_4),
    /**
     * TODO Replace with dynamic value
     */
    var txtExpanse: String? = MyApp.getInstance().resources.getString(R.string.lbl_expense),
    /**
     * TODO Replace with dynamic value
     */
    var txtIncome: String? = MyApp.getInstance().resources.getString(R.string.lbl_income),
    /**
     * TODO Replace with dynamic value
     */
    var txtUtilities: String? = MyApp.getInstance().resources.getString(R.string.lbl_shopping),
    /**
     * TODO Replace with dynamic value
     */
    var txtPriceOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_120),
    /**
     * TODO Replace with dynamic value
     */
    var txtUtilitiesOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_subcription),
    /**
     * TODO Replace with dynamic value
     */
    var txtPriceTwo: String? = MyApp.getInstance().resources.getString(R.string.lbl_12),
    /**
     * TODO Replace with dynamic value
     */
    var txtUtilitiesTwo: String? = MyApp.getInstance().resources.getString(R.string.lbl_food),
    /**
     * TODO Replace with dynamic value
     */
    var txtPriceThree: String? = MyApp.getInstance().resources.getString(R.string.lbl_4_40),
    /**
     * TODO Replace with dynamic value
     */
    var etDropdownMonthValue: String? = null
)
