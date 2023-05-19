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
    var txtLanguageSlo: String? = MyApp.getInstance().resources.getString(R.string.lbl_slovenian_slo),
    var txtLanguageEn: String? = MyApp.getInstance().resources.getString(R.string.lbl_english_en),
    var txtLanguageDe: String? = MyApp.getInstance().resources.getString(R.string.lbl_german_de),
    /**
     * TODO Replace with dynamic value
     */
    var etLanguageTwoValue: String? = null
)
