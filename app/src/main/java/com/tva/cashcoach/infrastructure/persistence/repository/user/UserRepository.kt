package com.tva.cashcoach.infrastructure.persistence.repository.user

import androidx.lifecycle.LiveData
import com.tva.cashcoach.infrastructure.model.user.User
import com.tva.cashcoach.infrastructure.model.user.UserDao

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAll()

    fun insert(user: User): Long {
        return userDao.insert(user)
    }

    fun get(uid: String): User? {
        return userDao.getByUid(uid)
    }

    fun getByUid(uid: String): User? {
        return userDao.getByUid(uid)
    }

    fun setDefaultWalletId(userId: String, walletId: Int) {
        userDao.setDefaultWalletId(userId, walletId)
    }

    fun getDefaultWalletId(uid: String): Int {
        return userDao.getDefaultWalletId(uid)
    }

    fun delete(user: User) {
        userDao.delete(user)
    }

    fun updateCurrencyByUid(uid: String, currency: String) {
        userDao.updateCurrencyByUid(uid, currency)
    }

    fun wipeTable() {
        userDao.wipeTable()
    }
}