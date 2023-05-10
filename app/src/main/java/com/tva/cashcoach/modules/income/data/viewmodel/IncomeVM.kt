package com.tva.cashcoach.modules.income.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.income.data.model.IncomeModel
import com.tva.cashcoach.modules.income.data.model.SpinnerDropdownCategoModel
import com.tva.cashcoach.modules.income.data.model.SpinnerDropdownWalletModel
import org.koin.core.KoinComponent

class IncomeVM : ViewModel(), KoinComponent {
    val incomeModel: MutableLiveData<IncomeModel> = MutableLiveData(IncomeModel())

    var navArguments: Bundle? = null

    val spinnerDropdownCategoList: MutableLiveData<MutableList<SpinnerDropdownCategoModel>> =
        MutableLiveData()

    val spinnerDropdownWalletList: MutableLiveData<MutableList<SpinnerDropdownWalletModel>> =
        MutableLiveData()
}
