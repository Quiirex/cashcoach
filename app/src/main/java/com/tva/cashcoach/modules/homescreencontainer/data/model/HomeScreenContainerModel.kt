package com.tva.cashcoach.modules.homescreencontainer.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class HomeScreenContainerModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtHomeOne: String? = App.getInstance().resources.getString(R.string.lbl_home),
    /**
     * TODO Replace with dynamic value
     */
    var txtTransaction: String? = App.getInstance().resources.getString(R.string.lbl_transactions),
    /**
     * TODO Replace with dynamic value
     */
    var txtReport: String? = App.getInstance().resources.getString(R.string.lbl_report),
    /**
     * TODO Replace with dynamic value
     */
    var txtProfile: String? = App.getInstance().resources.getString(R.string.lbl_profile)

)
