package com.tva.cashcoach.infrastructure.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tva.cashcoach.infrastructure.application.App
import com.tva.cashcoach.infrastructure.persistence.AppDatabase
import com.tva.cashcoach.infrastructure.utility.PreferenceHelper
import org.koin.android.ext.android.get

/**
 * Base class for activities that using databind feature to bind the view
 * also Implements [BaseControllerFunctionsImpl] interface
 * @param T A class that extends [ViewDataBinding] that will be used by the activity layout binding view.
 * @param layoutId the resource layout view going to bind with the [binding] variable
 */
abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    AppCompatActivity(), BaseControllerFunctionsImpl {

    /**
     * activity layout view binding object
     */
    lateinit var binding: T

    lateinit var appDb: AppDatabase

    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@BaseActivity, layoutId) as T
        binding.lifecycleOwner = this
        appDb = AppDatabase.getDatabase(this)
        preferenceHelper = App.getInstance().get()
        addObservers()
        setUpClicks()
        onInitialized()
    }
}