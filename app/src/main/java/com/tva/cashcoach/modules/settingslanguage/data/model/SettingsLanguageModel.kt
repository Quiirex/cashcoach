package com.tva.cashcoach.modules.settingslanguage.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class SettingsLanguageModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = MyApp.getInstance().resources.getString(R.string.lbl_language),
    /**
     * TODO Replace with dynamic value
     */
    var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.lbl_english_en),
    /**
     * TODO Replace with dynamic value
     */
    var txtLanguageOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_slovenian_slo),
    /**
     * TODO Replace with dynamic value
     */
    var etLanguageTwoValue: String? = null
)
