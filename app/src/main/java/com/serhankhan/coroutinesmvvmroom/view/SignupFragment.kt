package com.serhankhan.coroutinesmvvmroom.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.serhankhan.coroutinesmvvmroom.R
import com.serhankhan.coroutinesmvvmroom.viewmodel.SignUpViewModel


class SignupFragment : Fragment(),View.OnClickListener {


    private lateinit var viewModel:SignUpViewModel
    private lateinit var signUpButton:Button
    private lateinit var goToLoginButton:Button
    private lateinit var signUpUserName:EditText
    private lateinit var password:EditText
    private lateinit var otherInfo:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewElements(view)
        initViewModel()
        observeViewModel()
    }

    private fun initViewElements(view: View) {
        signUpButton = view.findViewById(R.id.signupBtn)
        goToLoginButton =  view.findViewById(R.id.gotoLoginBtn)

        signUpUserName = view.findViewById(R.id.signupUsername)
        password = view.findViewById(R.id.signupPassword)

        otherInfo = view.findViewById(R.id.otherInfo)

        signUpButton.setOnClickListener(this)
        goToLoginButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.signupBtn -> onSignup(p0)
            R.id.gotoLoginBtn -> onGotoLogin(p0)
        }
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
    }


    private fun observeViewModel(){
        viewModel.signupComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            Toast.makeText(activity,"SignUp Complete",Toast.LENGTH_SHORT).show()
            val action = SignupFragmentDirections.actionGoToMain()
            Navigation.findNavController(signUpUserName).navigate(action)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity,"Error $error",Toast.LENGTH_SHORT).show()
        })
    }

    private fun onSignup(v: View?){
        v?.let {
            val usernameTxt = signUpUserName.text.toString()
            val passwordTxt = password.text.toString()
            val otherInfoTxt = otherInfo.text.toString()

            if(usernameTxt.isNullOrEmpty() || passwordTxt.isNullOrEmpty() || otherInfoTxt.isNullOrEmpty()){
                Toast.makeText(activity,getString(R.string.please_fill_all_fields),Toast.LENGTH_SHORT).show()
            }else{
                viewModel.signup(usernameTxt,passwordTxt,otherInfoTxt)
            }
        }
    }

    private fun onGotoLogin(v: View?) {
        v?.let {
            val action = SignupFragmentDirections.actionGoToLogin()
            Navigation.findNavController(it).navigate(action)
        }
    }

}
