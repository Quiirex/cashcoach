package com.tva.cashcoach.modules.transaction.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class ListquestionRowModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtSalary: String? = MyApp.getInstance().resources.getString(R.string.lbl_salary),
    /**
     * TODO Replace with dynamic value
     */
    var txtBuyanAvocadoThree: String? =
        MyApp.getInstance().resources.getString(R.string.lbl_salary_for_july),
    /**
     * TODO Replace with dynamic value
     */
    var txtPriceThree: String? = MyApp.getInstance().resources.getString(R.string.lbl_1_6802),
    /**
     * TODO Replace with dynamic value
     */
    var txtTimeThree: String? = MyApp.getInstance().resources.getString(R.string.lbl_04_30)

)
