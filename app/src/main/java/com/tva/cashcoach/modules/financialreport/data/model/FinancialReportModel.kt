package com.tva.cashcoach.modules.financialreport.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class FinancialReportModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.msg_financial_repor),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = App.getInstance().resources.getString(R.string.lbl_136_4),
    /**
     * TODO Replace with dynamic value
     */
    var txtExpanse: String? = App.getInstance().resources.getString(R.string.lbl_expense),
    /**
     * TODO Replace with dynamic value
     */
    var txtIncome: String? = App.getInstance().resources.getString(R.string.lbl_income),

    var txtGraphBudget: String? = App.getInstance().resources.getString(R.string.lbl_graph_budget),
    var txtGraphIncome: String? = App.getInstance().resources.getString(R.string.lbl_graph_income),
    var txtGraphExpense: String? = App.getInstance().resources.getString(R.string.lbl_graph_expense)


)
