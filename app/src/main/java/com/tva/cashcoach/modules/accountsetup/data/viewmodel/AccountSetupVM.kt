package com.tva.cashcoach.modules.accountsetup.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.accountsetup.`data`.model.AccountSetupModel
import org.koin.core.KoinComponent

class AccountSetupVM : ViewModel(), KoinComponent {
    val accountSetupModel: MutableLiveData<AccountSetupModel> = MutableLiveData(AccountSetupModel())

    var navArguments: Bundle? = null
}
