package com.tva.cashcoach.modules.settingslanguage.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.settingslanguage.`data`.model.SettingsLanguageModel
import org.koin.core.KoinComponent

class SettingsLanguageVM : ViewModel(), KoinComponent {
    val settingsLanguageModel: MutableLiveData<SettingsLanguageModel> =
        MutableLiveData(SettingsLanguageModel())

    var navArguments: Bundle? = null
}
