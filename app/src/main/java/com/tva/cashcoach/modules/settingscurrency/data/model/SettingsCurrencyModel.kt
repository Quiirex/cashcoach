package com.tva.cashcoach.modules.settingscurrency.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.MyApp

data class SettingsCurrencyModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = MyApp.getInstance().resources.getString(R.string.lbl_currency),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = MyApp.getInstance().resources.getString(R.string.lbl_eur),
    /**
     * TODO Replace with dynamic value
     */
    var txtEur: String? = MyApp.getInstance().resources.getString(R.string.lbl_eur),
    var txtUsd: String? = MyApp.getInstance().resources.getString(R.string.lbl_usd)

)
