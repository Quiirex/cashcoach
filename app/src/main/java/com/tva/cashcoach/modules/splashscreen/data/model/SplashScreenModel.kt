package com.tva.cashcoach.modules.splashscreen.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class SplashScreenModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtCashCoach: String? = MyApp.getInstance().resources.getString(R.string.lbl_cashcoach)

)
