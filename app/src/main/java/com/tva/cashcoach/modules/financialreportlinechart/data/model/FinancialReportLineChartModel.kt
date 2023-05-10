package com.tva.cashcoach.modules.financialreportlinechart.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class FinancialReportLineChartModel(
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
    var txtIncome: String? = MyApp.getInstance().resources.getString(R.string.lbl_income)

)
