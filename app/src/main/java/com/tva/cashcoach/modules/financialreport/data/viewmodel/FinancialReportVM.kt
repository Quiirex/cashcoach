package com.tva.cashcoach.modules.financialreport.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.financialreport.data.model.FinancialReportModel
import org.koin.core.KoinComponent

class FinancialReportVM : ViewModel(), KoinComponent {
    val financialReportModel: MutableLiveData<FinancialReportModel> =
        MutableLiveData(FinancialReportModel())

    var navArguments: Bundle? = null
}
