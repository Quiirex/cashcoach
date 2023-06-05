package com.tva.cashcoach.modules.settingscurrency.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class SettingsCurrencyModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.lbl_currency),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = App.getInstance().resources.getString(R.string.lbl_eur),
    /**
     * TODO Replace with dynamic value
     */
    var txtEur: String? = App.getInstance().resources.getString(R.string.lbl_eur),
    var txtUsd: String? = App.getInstance().resources.getString(R.string.lbl_usd)

)
