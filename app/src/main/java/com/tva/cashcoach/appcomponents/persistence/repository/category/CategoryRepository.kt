package com.tva.cashcoach.appcomponents.persistence.repository.category

import com.tva.cashcoach.appcomponents.model.category.Category
import com.tva.cashcoach.appcomponents.model.category.CategoryDao

class CategoryRepository(private val categoryDao: CategoryDao) {

    fun getAll() = categoryDao.getAll()

    fun insert(category: Category) {
        categoryDao.insert(category)
    }

    fun insertAll(categories: List<Category>) {
        categoryDao.insertAll(categories)
    }

    fun delete(category: Category) {
        categoryDao.delete(category)
    }

    fun update(category: Category) {
        categoryDao.update(category)
    }

    fun wipeTable() {
        categoryDao.wipeTable()
    }
}