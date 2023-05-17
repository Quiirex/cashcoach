package com.tva.cashcoach.appcomponents.persistence.repository.wallet

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.model.wallet.WalletDao

class WalletRepository(private val walletDao: WalletDao) {

    val allWallets: LiveData<List<Wallet>> = walletDao.getAll()

    suspend fun insert(wallet: Wallet) {
        walletDao.insert(wallet)
    }

    suspend fun delete(wallet: Wallet) {
        walletDao.delete(wallet)
    }

    suspend fun update(wallet: Wallet) {
        walletDao.update(wallet)
    }

    suspend fun wipeTable() {
        walletDao.wipeTable()
    }
}