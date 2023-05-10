package com.tva.cashcoach.modules.signupsuccess.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.signupsuccess.`data`.model.SignupSuccessModel
import org.koin.core.KoinComponent

class SignupSuccessVM : ViewModel(), KoinComponent {
    val signupSuccessModel: MutableLiveData<SignupSuccessModel> =
        MutableLiveData(SignupSuccessModel())

    var navArguments: Bundle? = null
}
