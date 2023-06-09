package com.tva.cashcoach.modules.homescreen.data.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.homescreen.data.model.HomeScreenModel
import org.koin.core.KoinComponent

class HomeScreenVM : ViewModel(), KoinComponent {
    val homeScreenModel: MutableLiveData<HomeScreenModel> = MutableLiveData(HomeScreenModel())

    var navArguments: Bundle? = null
}
