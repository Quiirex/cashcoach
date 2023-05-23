package com.tva.cashcoach.appcomponents.persistence.repository.user

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.user.User
import com.tva.cashcoach.appcomponents.model.user.UserDao

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAll()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    fun get(uid: String): User? {
        return userDao.getByUid(uid)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    suspend fun wipeTable() {
        userDao.wipeTable()
    }
}