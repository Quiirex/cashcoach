package com.tva.cashcoach.infrastructure.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tva.cashcoach.infrastructure.application.App
import com.tva.cashcoach.infrastructure.persistence.AppDatabase
import com.tva.cashcoach.infrastructure.utility.PreferenceHelper
import org.koin.android.ext.android.get

/**
 * Base class for fragments that using databind feature to bind the view
 * also Implements [BaseControllerFunctionsImpl] interface
 * @param T A class that extends [ViewDataBinding] that will be used by the fragment layout binding view.
 * @param layoutId the resource layout view going to bind with the [binding] variable
 */
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment(),
    BaseControllerFunctionsImpl {
    lateinit var binding: T

    lateinit var appDb: AppDatabase

    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = requireActivity()
        appDb = AppDatabase.getDatabase(requireContext())
        preferenceHelper = App.getInstance().get()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        setUpClicks()
        onInitialized()
    }
}