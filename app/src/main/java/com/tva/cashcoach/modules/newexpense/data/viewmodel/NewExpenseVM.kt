package com.tva.cashcoach.modules.newexpense.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.newexpense.data.model.NewExpenseModel
import com.tva.cashcoach.modules.newexpense.data.model.SpinnerDropdownCategoModel
import com.tva.cashcoach.modules.newexpense.data.model.SpinnerDropdownWalletModel
import org.koin.core.KoinComponent

class NewExpenseVM : ViewModel(), KoinComponent {
    val newExpenseModel: MutableLiveData<NewExpenseModel> = MutableLiveData(NewExpenseModel())

    var navArguments: Bundle? = null

    val spinnerDropdownCategoList: MutableLiveData<MutableList<SpinnerDropdownCategoModel>> =
        MutableLiveData()

    val spinnerDropdownWalletList: MutableLiveData<MutableList<SpinnerDropdownWalletModel>> =
        MutableLiveData()
}
