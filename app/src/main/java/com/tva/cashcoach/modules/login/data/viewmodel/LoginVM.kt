package com.tva.cashcoach.modules.login.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.tva.cashcoach.modules.login.`data`.model.LoginModel
import org.koin.core.KoinComponent

class LoginVM : ViewModel(), KoinComponent {
    val loginModel: MutableLiveData<LoginModel> = MutableLiveData(LoginModel())


    var navArguments: Bundle? = null


    lateinit var googleAuthResponse: GoogleSignInAccount
}
