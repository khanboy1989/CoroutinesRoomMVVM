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

class LoginViewModel(application: Application):AndroidViewModel(application) {


    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(getApplication()).userDao() }

    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()


    fun login(username:String,password:String){
        coroutineScope.launch {
            val user = db.getUser(username)
            if(user == null){
                withContext(Dispatchers.Main){
                    error.postValue("User not found")
                }
            }else {
                if (user.passwordHash == password.hashCode()){
                    Log.d("User login",user.passwordHash.toString() + "   " +   password.hashCode().toString())
                    LoginState.login(user)
                    withContext(Dispatchers.Main){
                        loginComplete.postValue(true)
                    }
                }else {
                    withContext(Dispatchers.Main){
                        error.postValue("Password is incorrect")
                    }
                }
            }
        }
    }
}