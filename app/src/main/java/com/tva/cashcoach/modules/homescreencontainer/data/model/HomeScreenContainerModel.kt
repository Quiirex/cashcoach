package com.tva.cashcoach.modules.homescreencontainer.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class HomeScreenContainerModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtHomeOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_home),
    /**
     * TODO Replace with dynamic value
     */
    var txtTransaction: String? = MyApp.getInstance().resources.getString(R.string.lbl_transaction),
    /**
     * TODO Replace with dynamic value
     */
    var txtReport: String? = MyApp.getInstance().resources.getString(R.string.lbl_report),
    /**
     * TODO Replace with dynamic value
     */
    var txtProfile: String? = MyApp.getInstance().resources.getString(R.string.lbl_profile)

)
