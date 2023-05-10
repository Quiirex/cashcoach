package com.tva.cashcoach.modules.transaction.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.transaction.data.model.ListquestionRowModel
import com.tva.cashcoach.modules.transaction.data.model.ListtrashRowModel
import com.tva.cashcoach.modules.transaction.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.transaction.data.model.TransactionModel
import org.koin.core.KoinComponent

class TransactionVM : ViewModel(), KoinComponent {
    val transactionModel: MutableLiveData<TransactionModel> = MutableLiveData(TransactionModel())

    var navArguments: Bundle? = null

    val spinnerDropdownMonthList: MutableLiveData<MutableList<SpinnerDropdownMonthModel>> =
        MutableLiveData()

    val listtrashList: MutableLiveData<MutableList<ListtrashRowModel>> =
        MutableLiveData(mutableListOf())

    val listquestionList: MutableLiveData<MutableList<ListquestionRowModel>> =
        MutableLiveData(mutableListOf())
}
