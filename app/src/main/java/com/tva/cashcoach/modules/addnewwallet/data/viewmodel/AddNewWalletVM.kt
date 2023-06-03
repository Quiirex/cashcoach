package com.tva.cashcoach.modules.addnewwallet.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.addnewwallet.data.model.AddNewWalletModel
import com.tva.cashcoach.modules.addnewwallet.data.model.SpinnerWalletModel
import org.koin.core.KoinComponent

class AddNewWalletVM : ViewModel(), KoinComponent {
    val addNewWalletModel: MutableLiveData<AddNewWalletModel> = MutableLiveData(AddNewWalletModel())

    var navArguments: Bundle? = null

    val spinnerWallet: MutableLiveData<MutableList<SpinnerWalletModel>> =
        MutableLiveData()
}
