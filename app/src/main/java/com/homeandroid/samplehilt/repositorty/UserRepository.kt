package com.homeandroid.samplehilt.repositorty

import com.homeandroid.samplehilt.db.AppDb
import com.homeandroid.samplehilt.db.User
import javax.inject.Inject

class UserRepository @Inject constructor(appDb: AppDb) {
    private val tododao = appDb.userDao()
    fun saveUser(user: User) {
        tododao.insert(user)
    }
}