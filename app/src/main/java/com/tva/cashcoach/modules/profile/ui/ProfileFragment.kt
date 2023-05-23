package com.tva.cashcoach.modules.profile.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.fragment.app.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.auth.AuthHelper
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.appcomponents.model.user.UserDao
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.appcomponents.utility.ImageHelper
import com.tva.cashcoach.databinding.FragmentProfileBinding
import com.tva.cashcoach.modules.login.ui.LoginActivity
import com.tva.cashcoach.modules.profile.data.viewmodel.ProfileVM
import com.tva.cashcoach.modules.settings.ui.SettingsActivity
import com.tva.cashcoach.modules.wallets.ui.WalletsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileVM by viewModels()

    private val REQUEST_CODE_ACCOUNTS_ACTIVITY: Int = 235

    private val REQUEST_CODE_SETTINGS_ACTIVITY: Int = 236

    private val REQUEST_CODE_LOGIN_ACTIVITY: Int = 265

    private lateinit var auth: AuthHelper

    private lateinit var imageHelper: ImageHelper

    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    override fun onInitialized() {
        viewModel.navArguments = arguments
        binding.profileVM = viewModel
        auth = AuthHelper(ComponentActivity(), {}, {}, appDb, preferenceHelper)
        imageHelper = ImageHelper()

        userDao = appDb.getUserDao()
        userRepository = UserRepository(userDao)

        var currentUserFirstName = ""
        var currentUserLastName = ""
        var currentUserAvatarURL = ""
        var currentUserAvatarBitmap: Bitmap?

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                currentUserFirstName = userRepository.get(
                    preferenceHelper.getString("curr_user_id", "")
                )?.name.toString()

                currentUserLastName = userRepository.get(
                    preferenceHelper.getString("curr_user_id", "")
                )?.surname.toString()

                currentUserAvatarURL = userRepository.get(
                    preferenceHelper.getString("curr_user_id", "")
                )?.avatar.toString()

                currentUserAvatarBitmap = imageHelper.getBitmapFromURL(currentUserAvatarURL)
            }

            binding.txtNameSurname.text = "$currentUserFirstName $currentUserLastName"
            binding.imageAvatar.setImageBitmap(currentUserAvatarBitmap)
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
            try {
                auth.signOut()
            } catch (e: Exception) {
                Log.d("Auth", "Error signing out")
            }
            val destIntent = context?.let { it1 -> LoginActivity.getIntent(it1, null) }
            startActivityForResult(destIntent, REQUEST_CODE_LOGIN_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "PROFILE_FRAGMENT"

        fun getInstance(bundle: Bundle?): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
