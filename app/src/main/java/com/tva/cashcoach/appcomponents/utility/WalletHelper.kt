package com.tva.cashcoach.appcomponents.utility

import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.persistence.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WalletHelper {
    lateinit var appDb: AppDatabase

    /**
     * Adds a new wallet to the database.
     *
     * @param name The name of the wallet.
     * @param type The type of the wallet.
     * @param budget The budget of the wallet.
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun addWallet(name: String, type: String, budget: Double) {
        val wallet = Wallet(null, name, type, budget)
        GlobalScope.launch(Dispatchers.IO) {
//            appDb.walletDao().insert(wallet)
        }
    }
}