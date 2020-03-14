package com.serhankhan.coroutinesmvvmroom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.serhankhan.coroutinesmvvmroom.model.LoginState
import com.serhankhan.coroutinesmvvmroom.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application){

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(getApplication()).userDao()}

    val userDeleted = MutableLiveData<Boolean>()
    val signout = MutableLiveData<Boolean>()

    fun onSignout() {
        LoginState.logout()
        signout.postValue(true)
    }

    fun onDeleteUser() {
        coroutineScope.launch {
            LoginState.user?.let {user->
                db.deleteUser(user.id)
            }

            withContext(Dispatchers.Main){
                LoginState.logout()
                userDeleted.postValue(true)
            }
        }
    }

}