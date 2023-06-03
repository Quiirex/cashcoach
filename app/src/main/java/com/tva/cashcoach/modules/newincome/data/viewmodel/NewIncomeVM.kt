package com.tva.cashcoach.modules.newincome.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.newincome.data.model.NewIncomeModel
import com.tva.cashcoach.modules.newincome.data.model.SpinnerCategoryModel
import org.koin.core.KoinComponent

class NewIncomeVM : ViewModel(), KoinComponent {
    val newIncomeModel: MutableLiveData<NewIncomeModel> = MutableLiveData(
        NewIncomeModel()
    )

    var navArguments: Bundle? = null

    val spinnerCategoryList: MutableLiveData<MutableList<SpinnerCategoryModel>> =
        MutableLiveData()
}
