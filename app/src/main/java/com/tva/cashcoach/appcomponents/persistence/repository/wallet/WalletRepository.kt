package com.tva.cashcoach.appcomponents.persistence.repository.wallet

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.model.wallet.WalletDao

class WalletRepository(private val walletDao: WalletDao) {

    val allWallets: LiveData<List<Wallet>> = walletDao.getAll()

    fun insert(wallet: Wallet) {
        walletDao.insert(wallet)
    }

    fun delete(wallet: Wallet) {
        walletDao.delete(wallet)
    }

    fun update(wallet: Wallet) {
        walletDao.update(wallet)
    }

    fun wipeTable() {
        walletDao.wipeTable()
    }
}