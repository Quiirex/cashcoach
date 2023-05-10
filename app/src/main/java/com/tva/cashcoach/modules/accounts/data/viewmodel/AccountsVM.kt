package com.tva.cashcoach.modules.accounts.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.accounts.data.model.AccountsModel
import com.tva.cashcoach.modules.accounts.data.model.AccountsRowModel
import org.koin.core.KoinComponent

class AccountsVM : ViewModel(), KoinComponent {
    val accountsModel: MutableLiveData<AccountsModel> = MutableLiveData(AccountsModel())

    var navArguments: Bundle? = null

    val accountsList: MutableLiveData<MutableList<AccountsRowModel>> =
        MutableLiveData(mutableListOf())
}
