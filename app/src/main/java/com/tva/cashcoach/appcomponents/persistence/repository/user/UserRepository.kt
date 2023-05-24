package com.tva.cashcoach.appcomponents.persistence.repository.user

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.user.User
import com.tva.cashcoach.appcomponents.model.user.UserDao

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAll()

    fun insert(user: User) {
        userDao.insert(user)
    }

    fun get(uid: String): User? {
        return userDao.getByUid(uid)
    }

    fun delete(user: User) {
        userDao.delete(user)
    }

    fun updateCurrencyByUid(uid: String, currency: String) {
        userDao.updateCurrencyByUid(uid, currency)
    }

    fun updateLanguageByUid(uid: String, language: String) {
        userDao.updateLanguageByUid(uid, language)
    }

    fun wipeTable() {
        userDao.wipeTable()
    }
}