package com.tva.cashcoach.modules.settingstheme.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class SettingsThemeModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = MyApp.getInstance().resources.getString(R.string.lbl_theme),
    /**
     * TODO Replace with dynamic value
     */
    var txtThemeDark: String? = MyApp.getInstance().resources.getString(R.string.lbl_dark),
    var txtThemeLight: String? = MyApp.getInstance().resources.getString(R.string.lbl_light),
    var txtThemeDevice: String? = MyApp.getInstance().resources.getString(R.string.msg_use_device_them),
    /**
     * TODO Replace with dynamic value
     */
    var txtListItemSel: String? = MyApp.getInstance().resources.getString(R.string.lbl_dark),
    /**
     * TODO Replace with dynamic value
     */
    var txtListItemSelOne: String? =
        MyApp.getInstance().resources.getString(R.string.msg_use_device_them)

)
