package com.tva.cashcoach.modules.homescreen.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.databinding.FragmentHomeScreenBinding
import com.tva.cashcoach.extensions.loadFragment
import com.tva.cashcoach.modules.homescreen.data.model.ListeyeRowModel
import com.tva.cashcoach.modules.homescreen.data.model.ListframefiveRowModel
import com.tva.cashcoach.modules.homescreen.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.homescreen.data.viewmodel.HomeScreenVM
import com.tva.cashcoach.modules.profile.ui.ProfileFragment

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>(R.layout.fragment_home_screen) {
    private val viewModel: HomeScreenVM by viewModels<HomeScreenVM>()

    private val REQUEST_CODE_PROFILE_FRAGMENT: Int = 668


    override fun onInitialized(): Unit {
        viewModel.navArguments = arguments
        viewModel.spinnerDropdownMonthList.value = mutableListOf(
            SpinnerDropdownMonthModel("Item1"),
            SpinnerDropdownMonthModel("Item2"),
            SpinnerDropdownMonthModel("Item3"),
            SpinnerDropdownMonthModel("Item4"),
            SpinnerDropdownMonthModel("Item5")
        )
        val spinnerDropdownMonthAdapter =
            SpinnerDropdownMonthAdapter(
                requireActivity(),
                R.layout.spinner_item,
                viewModel.spinnerDropdownMonthList.value ?: mutableListOf()
            )
        binding.spinnerDropdownMonth.adapter = spinnerDropdownMonthAdapter
        val listeyeAdapter = ListeyeAdapter(viewModel.listeyeList.value ?: mutableListOf())
        binding.recyclerListeye.adapter = listeyeAdapter
        listeyeAdapter.setOnItemClickListener(
            object : ListeyeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: ListeyeRowModel) {
                    onClickRecyclerListeye(view, position, item)
                }
            }
        )
        viewModel.listeyeList.observe(requireActivity()) {
            listeyeAdapter.updateData(it)
        }
        val listframefiveAdapter =
            ListframefiveAdapter(viewModel.listframefiveList.value ?: mutableListOf())
        binding.recyclerListframefive.adapter = listframefiveAdapter
        listframefiveAdapter.setOnItemClickListener(
            object : ListframefiveAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: ListframefiveRowModel) {
                    onClickRecyclerListframefive(view, position, item)
                }
            }
        )
        viewModel.listframefiveList.observe(requireActivity()) {
            listframefiveAdapter.updateData(it)
        }
        binding.homeScreenVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.imageAvatar.setOnClickListener {
            val destFragment = ProfileFragment.getInstance(null)
            requireActivity().loadFragment(
                R.id.fragmentContainer,
                destFragment,
                bundle = destFragment.arguments,
                tag = ProfileFragment.TAG,
                addToBackStack = true,
                add = true,
                enter = null,
                exit = null,
            )
            requireActivity().onBackPressed()
        }
    }

    fun onClickRecyclerListeye(
        view: View,
        position: Int,
        item: ListeyeRowModel
    ): Unit {
        when (view.id) {
        }
    }

    fun onClickRecyclerListframefive(
        view: View,
        position: Int,
        item: ListframefiveRowModel
    ): Unit {
        when (view.id) {
        }
    }

    companion object {
        const val TAG: String = "HOME_SCREEN_FRAGMENT"


        fun getInstance(bundle: Bundle?): HomeScreenFragment {
            val fragment = HomeScreenFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
