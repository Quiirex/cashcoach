package com.tva.cashcoach.modules.transaction.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.transaction.data.model.ListtrashRowModel
import com.tva.cashcoach.modules.transaction.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import org.koin.core.KoinComponent

class TransactionVM : ViewModel(), KoinComponent {
    val transactionRowModel: MutableLiveData<TransactionRowModel> =
        MutableLiveData(TransactionRowModel())

    var navArguments: Bundle? = null

    val spinnerDropdownMonthList: MutableLiveData<MutableList<SpinnerDropdownMonthModel>> =
        MutableLiveData()

    val listtrashList: MutableLiveData<MutableList<ListtrashRowModel>> =
        MutableLiveData(mutableListOf())
}
