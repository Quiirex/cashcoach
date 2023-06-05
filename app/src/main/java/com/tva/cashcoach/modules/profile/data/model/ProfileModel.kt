package com.tva.cashcoach.modules.profile.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.MyApp

data class ProfileModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtName: String? = MyApp.getInstance().resources.getString(R.string.lbl_name),
    /**
     * TODO Replace with dynamic value
     */
    var txtNameSurname: String? = MyApp.getInstance().resources.getString(R.string.lbl_name_surname),

    )
