package com.tva.cashcoach.appcomponents.persistence.repository.wallet

import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.model.wallet.WalletDao

class WalletRepository(private val walletDao: WalletDao) {

    fun insert(wallet: Wallet): Long {
        return walletDao.insert(wallet)
    }

    fun getAllByUid(uid: String): List<Wallet> {
        return walletDao.getAllByUid(uid)
    }

    fun getById(id: Int): Wallet {
        return walletDao.getById(id)
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