package com.serhankhan.coroutinesmvvmroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.serhankhan.coroutinesmvvmroom.model.LoginState
import com.serhankhan.coroutinesmvvmroom.model.User
import com.serhankhan.coroutinesmvvmroom.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(getApplication()).userDao() }

    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(username: String, password: String, info: String) {

        coroutineScope.launch {
            val userExists = db.getUser(username)

            if(userExists != null){
                withContext(Dispatchers.Main){
                    error.value = "User already exists"
                }
            }else{
                val user = User(username,password.hashCode(),info)
                val userId = db.insertUser(user)
                user.id = userId
                LoginState.login(user)
                withContext(Dispatchers.Main){
                    signupComplete.value = true
                }
            }
        }
    }

}