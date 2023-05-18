package com.tva.cashcoach.modules.newincome.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.newincome.data.model.NewIncomeModel
import com.tva.cashcoach.modules.newincome.data.model.SpinnerDropdownCategoModel
import com.tva.cashcoach.modules.newincome.data.model.SpinnerDropdownWalletModel
import org.koin.core.KoinComponent

class NewIncomeVM : ViewModel(), KoinComponent {
    val newIncomeModel: MutableLiveData<NewIncomeModel> = MutableLiveData(
        NewIncomeModel()
    )

    var navArguments: Bundle? = null

    val spinnerDropdownCategoList: MutableLiveData<MutableList<SpinnerDropdownCategoModel>> =
        MutableLiveData()

    val spinnerDropdownWalletList: MutableLiveData<MutableList<SpinnerDropdownWalletModel>> =
        MutableLiveData()
}
