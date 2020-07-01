package com.homeandroid.samplehilt.ui.save

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeandroid.samplehilt.repositorty.UserRepository
import com.homeandroid.samplehilt.db.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewmodel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var user = User()
    val status: MutableLiveData<Boolean> = MutableLiveData()
    fun save() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.saveUser(user)
                status.postValue(true)
            }
        }
    }
}