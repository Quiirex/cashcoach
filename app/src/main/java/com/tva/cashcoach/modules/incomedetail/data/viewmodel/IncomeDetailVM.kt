package com.tva.cashcoach.modules.incomedetail.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.incomedetail.`data`.model.IncomeDetailModel
import org.koin.core.KoinComponent

class IncomeDetailVM : ViewModel(), KoinComponent {
    val incomeDetailModel: MutableLiveData<IncomeDetailModel> = MutableLiveData(IncomeDetailModel())

    var navArguments: Bundle? = null
}
