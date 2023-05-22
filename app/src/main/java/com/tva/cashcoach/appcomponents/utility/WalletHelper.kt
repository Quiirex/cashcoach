package com.tva.cashcoach.appcomponents.utility

import android.util.Log
import com.tva.cashcoach.appcomponents.di.MyApp
import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class WalletHelper(private val walletRepository: WalletRepository) {
    var preferenceHelper: PreferenceHelper = MyApp.getInstance().get()

    /**
     * Adds a new wallet to the database.
     *
     * @param name The name of the wallet.
     * @param type The type of the wallet.
     * @param budget The budget of the wallet.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun addWallet(name: String, type: String, budget: Double): Boolean {
        try {
            val userId = preferenceHelper.getString("curr_user_id", "")
            Log.w("WalletHelper", "current user id: $userId")
            if (userId == "") return false
            val wallet = Wallet(null, name, type, budget, userId)
            GlobalScope.launch(Dispatchers.IO) {
                walletRepository.insert(wallet)
            }
            if (preferenceHelper.getString("curr_wallet_id", "") == "")
                preferenceHelper.putString("curr_wallet_id", wallet.id.toString())
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
