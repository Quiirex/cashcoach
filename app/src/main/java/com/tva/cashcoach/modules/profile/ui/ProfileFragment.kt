package com.tva.cashcoach.modules.profile.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.FragmentProfileBinding
import com.tva.cashcoach.infrastructure.auth.AuthHelper
import com.tva.cashcoach.infrastructure.base.BaseFragment
import com.tva.cashcoach.infrastructure.model.user.UserDao
import com.tva.cashcoach.infrastructure.persistence.repository.user.UserRepository
import com.tva.cashcoach.infrastructure.ui.loadImageFromURL
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.loadingscreen.ui.LoadingScreenFragment
import com.tva.cashcoach.modules.login.ui.LoginActivity
import com.tva.cashcoach.modules.profile.data.viewmodel.ProfileVM
import com.tva.cashcoach.modules.settings.ui.SettingsActivity
import com.tva.cashcoach.modules.wallets.ui.WalletsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileVM by viewModels()

    private val REQUEST_CODE_ACCOUNTS_ACTIVITY: Int = 235

    private val REQUEST_CODE_SETTINGS_ACTIVITY: Int = 236

    private val REQUEST_CODE_LOGIN_ACTIVITY: Int = 265

    private lateinit var authHelper: AuthHelper

    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    private val loadingDialogFragment by lazy { LoadingScreenFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navArguments = arguments
        binding.profileVM = viewModel

        userDao = appDb.getUserDao()
        userRepository = UserRepository(userDao)

        authHelper =
            AuthHelper(ComponentActivity(), {}, {}, preferenceHelper, userRepository)

        val currentUserId = preferenceHelper.getString("curr_user_uid", "")

        lifecycleScope.launch {
            val currentUser = withContext(Dispatchers.IO) {
                userRepository.get(currentUserId)
            }

            val currentUserFirstName = currentUser?.name ?: ""
            val currentUserLastName = currentUser?.surname ?: ""

            loadImageFromURL(
                binding.imageAvatar,
                currentUser?.avatar
                    ?: "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg",
                null,
                null,
                0.0f,
                true
            )

            binding.txtNameSurname.text = "$currentUserFirstName $currentUserLastName"
        }
    }

    override fun setUpClicks() {
        binding.linearRowAccounts.setOnClickListener {
            val destIntent = context?.let { it1 -> WalletsActivity.getIntent(it1, null) }
            startActivityForResult(destIntent, REQUEST_CODE_ACCOUNTS_ACTIVITY)
        }
        binding.linearRowsettings.setOnClickListener {
            val destIntent = context?.let { it1 -> SettingsActivity.getIntent(it1, null) }
            startActivityForResult(destIntent, REQUEST_CODE_SETTINGS_ACTIVITY)
        }
        binding.linearRowarrowright.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.lbl_log_out))
            builder.setMessage(getString(R.string.do_you_want_to_logout))
            builder.setPositiveButton(R.string.yes) { _, _ ->
                try {
                    if (!loadingDialogFragment.isAdded) {
                        loadingDialogFragment.show(
                            requireActivity().supportFragmentManager,
                            "loader"
                        )
                    }
                    authHelper.signOut()
                } catch (e: Exception) {
                    Log.d("Auth", "Error signing out")
                }
                val destIntent = context?.let { it1 -> LoginActivity.getIntent(it1, null) }
                startActivityForResult(destIntent, REQUEST_CODE_LOGIN_ACTIVITY)
            }
            builder.setNegativeButton(R.string.no) { _, _ ->
                // Do nothing
            }
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_corners)
            dialog.setOnShowListener {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(android.R.color.black))
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(android.R.color.black))
            }
            dialog.show()
        }
    }

    private fun updateButtonColors() {
        (activity as? HomeScreenContainerActivity)?.updateButtonColors(TAG)
    }

    override fun onResume() {
        super.onResume()
        currentFragmentTag = TAG
        updateButtonColors()
    }

    override fun onPause() {
        super.onPause()
        currentFragmentTag = null
    }

    companion object {
        const val TAG: String = "PROFILE_FRAGMENT"

        var currentFragmentTag: String? = null

        fun getInstance(bundle: Bundle?): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
