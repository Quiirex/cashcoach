package com.tva.cashcoach.modules.homescreen.data.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.homescreen.data.model.HomeScreenModel
import com.tva.cashcoach.modules.homescreen.data.model.ListframefiveRowModel
import com.tva.cashcoach.modules.homescreen.data.model.SpinnerDropdownMonthModel
import org.koin.core.KoinComponent

class HomeScreenVM : ViewModel(), KoinComponent {
    val homeScreenModel: MutableLiveData<HomeScreenModel> = MutableLiveData(HomeScreenModel())

    var navArguments: Bundle? = null

    val spinnerDropdownMonthList: MutableLiveData<MutableList<SpinnerDropdownMonthModel>> =
        MutableLiveData()

    val listframefiveList: MutableLiveData<MutableList<ListframefiveRowModel>> =
        MutableLiveData(mutableListOf())
}
