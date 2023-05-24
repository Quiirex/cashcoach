package com.tva.cashcoach.appcomponents.utility

import android.util.Log
import com.tva.cashcoach.appcomponents.di.MyApp
import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get

class WalletHelper(
    private val walletRepository: WalletRepository,
    private val userRepository: UserRepository
) {
    var preferenceHelper: PreferenceHelper = MyApp.getInstance().get()

    /**
     * Adds a new wallet to the database.
     *
     * @param name The name of the wallet.
     * @param type The type of the wallet.
     * @param budget The budget of the wallet.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun addWallet(name: String, type: String, budget: Double, callback: (Int) -> Unit) {
        try {
            val userId = preferenceHelper.getString("curr_user_uid", "")
            Log.w("WalletHelper", "current user id: $userId")
            if (userId == "") {
                callback(-1)
                return
            }
            val wallet = Wallet(null, name, type, budget, userId)
            GlobalScope.launch(Dispatchers.IO) {
                val insertedWalletId = walletRepository.insert(wallet).toInt()
                withContext(Dispatchers.Main) {
                    callback(insertedWalletId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback(-1)
        }
    }


    /**
     * Sets default wallet of the current user
     * @param walletId The id of the wallet.
     * @return true if successful, false otherwise.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun setDefaultWallet(walletId: Int): Boolean {
        try {
            val userId = preferenceHelper.getString("curr_user_uid", "")
            if (userId == "") return false
            GlobalScope.launch(Dispatchers.IO) {
                userRepository.setDefaultWalletId(userId, walletId)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /**
     * Displays the total balance of all wallets of the current user.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun getTotalBalanceOfWallets(callback: (Double) -> Unit) {
        val userId = preferenceHelper.getString("curr_user_uid", "")
        if (userId == "") {
            callback(0.0)
            return
        }
        var totalBalance = 0.0
        GlobalScope.launch(Dispatchers.IO) {
            val wallets = walletRepository.getAllByUid(userId)
            for (wallet in wallets) {
                totalBalance += wallet.balance
            }
            withContext(Dispatchers.Main) {
                callback(totalBalance)
            }
        }
    }
}
