package com.tva.cashcoach.modules.financialreport.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.financialreport.data.model.FinancialReportModel
import com.tva.cashcoach.modules.financialreport.data.model.Listtrash1RowModel
import com.tva.cashcoach.modules.financialreport.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.financialreport.data.model.SpinnerDropdownTransaModel
import org.koin.core.KoinComponent

class FinancialReportVM : ViewModel(), KoinComponent {
    val financialReportModel: MutableLiveData<FinancialReportModel> =
        MutableLiveData(FinancialReportModel())

    var navArguments: Bundle? = null

    val spinnerDropdownMonthList: MutableLiveData<MutableList<SpinnerDropdownMonthModel>> =
        MutableLiveData()

    val spinnerDropdownTransaList: MutableLiveData<MutableList<SpinnerDropdownTransaModel>> =
        MutableLiveData()

    val listtrashList: MutableLiveData<MutableList<Listtrash1RowModel>> =
        MutableLiveData(mutableListOf())
}
