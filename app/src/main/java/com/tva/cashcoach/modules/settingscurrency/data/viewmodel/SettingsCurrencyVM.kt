package com.tva.cashcoach.modules.settingscurrency.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.settingscurrency.`data`.model.SettingsCurrencyModel
import org.koin.core.KoinComponent

class SettingsCurrencyVM : ViewModel(), KoinComponent {
    val settingsCurrencyModel: MutableLiveData<SettingsCurrencyModel> =
        MutableLiveData(SettingsCurrencyModel())

    var navArguments: Bundle? = null
}
