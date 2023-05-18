package com.tva.cashcoach.modules.wallets.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.wallets.data.model.WalletsModel
import com.tva.cashcoach.modules.wallets.data.model.WalletsRowModel
import org.koin.core.KoinComponent

class WalletsVM : ViewModel(), KoinComponent {
    val walletsModel: MutableLiveData<WalletsModel> = MutableLiveData(WalletsModel())

    var navArguments: Bundle? = null

    val accountsList: MutableLiveData<MutableList<WalletsRowModel>> =
        MutableLiveData(mutableListOf())
}
