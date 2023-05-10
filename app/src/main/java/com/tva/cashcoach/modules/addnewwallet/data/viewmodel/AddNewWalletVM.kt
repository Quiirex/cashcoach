package com.tva.cashcoach.modules.addnewwallet.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.addnewwallet.data.model.AddNewWalletModel
import com.tva.cashcoach.modules.addnewwallet.data.model.SpinnerDropdownWalletModel
import org.koin.core.KoinComponent

class AddNewWalletVM : ViewModel(), KoinComponent {
    val addNewWalletModel: MutableLiveData<AddNewWalletModel> = MutableLiveData(AddNewWalletModel())

    var navArguments: Bundle? = null

    val spinnerDropdownWalletList: MutableLiveData<MutableList<SpinnerDropdownWalletModel>> =
        MutableLiveData()
}
