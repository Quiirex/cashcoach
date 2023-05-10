package com.tva.cashcoach.modules.settingstheme.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.settingstheme.`data`.model.SettingsThemeModel
import org.koin.core.KoinComponent

class SettingsThemeVM : ViewModel(), KoinComponent {
    val settingsThemeModel: MutableLiveData<SettingsThemeModel> =
        MutableLiveData(SettingsThemeModel())

    var navArguments: Bundle? = null
}
