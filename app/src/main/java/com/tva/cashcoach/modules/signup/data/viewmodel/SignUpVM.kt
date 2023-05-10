package com.tva.cashcoach.modules.signup.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.tva.cashcoach.modules.signup.`data`.model.SignUpModel
import org.koin.core.KoinComponent

class SignUpVM : ViewModel(), KoinComponent {
    val signUpModel: MutableLiveData<SignUpModel> = MutableLiveData(SignUpModel())


    var navArguments: Bundle? = null


    lateinit var googleAuthResponse: GoogleSignInAccount
}
