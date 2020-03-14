package com.serhankhan.coroutinesmvvmroom.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.serhankhan.coroutinesmvvmroom.R
import com.serhankhan.coroutinesmvvmroom.viewmodel.LoginViewModel


class LoginFragment : Fragment(),View.OnClickListener {


    private lateinit var login_button:Button
    private lateinit var  go_to_signup:Button
    private lateinit var viewModel: LoginViewModel
    private lateinit var loginUserNameEditText:EditText
    private lateinit var loginPasswordEditText:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewElemenets(view)
        initViewModel()
        observeViewModel()
    }

    fun initViewElemenets(view:View){
        login_button = view.findViewById(R.id.loginBtn)
        go_to_signup = view.findViewById(R.id.gotoSignupBtn)

        loginUserNameEditText = view.findViewById(R.id.loginUsername)
        loginPasswordEditText = view.findViewById(R.id.loginPassword)

        login_button.setOnClickListener(this)
        go_to_signup.setOnClickListener(this)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private fun observeViewModel(){
        viewModel.loginComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            view?.let {
                Toast.makeText(activity,getString(R.string.login_complete),Toast.LENGTH_LONG).show()
                val action = LoginFragmentDirections.actionGoToMain()
                Navigation.findNavController(it).navigate(action)
            }

        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity,"Error:$error",Toast.LENGTH_SHORT).show()

        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.loginBtn->onLogin(p0)
            R.id.gotoSignupBtn-> onGotoSignup(p0)
        }
    }

    private fun onLogin(v: View?) {
        v?.let{
            val username = loginUserNameEditText.text.toString()
            val password = loginPasswordEditText.text.toString()
            if(username.isNullOrEmpty() || password.isNullOrEmpty()){
                Toast.makeText(activity,getString(R.string.please_fill_all_fields),Toast.LENGTH_SHORT).show()
            }else {
                viewModel.login(username,password)
            }
        }
    }

    private fun onGotoSignup(v: View?){
        v?.let {
            val action = LoginFragmentDirections.actionGoToSignup()
            Navigation.findNavController(it).navigate(action)
        }

    }
}
