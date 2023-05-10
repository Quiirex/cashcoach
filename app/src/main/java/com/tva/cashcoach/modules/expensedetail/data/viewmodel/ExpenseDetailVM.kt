package com.tva.cashcoach.modules.expensedetail.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.expensedetail.`data`.model.ExpenseDetailModel
import org.koin.core.KoinComponent

class ExpenseDetailVM : ViewModel(), KoinComponent {
    val expenseDetailModel: MutableLiveData<ExpenseDetailModel> =
        MutableLiveData(ExpenseDetailModel())

    var navArguments: Bundle? = null
}
