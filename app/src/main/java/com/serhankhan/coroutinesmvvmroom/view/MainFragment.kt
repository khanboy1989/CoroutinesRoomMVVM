package com.serhankhan.coroutinesmvvmroom.view


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.serhankhan.coroutinesmvvmroom.R
import com.serhankhan.coroutinesmvvmroom.model.LoginState
import com.serhankhan.coroutinesmvvmroom.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(),View.OnClickListener {


    private lateinit var viewModel: MainViewModel
    private lateinit var signOutButton:Button
    private lateinit var deleteUserButton:Button
    private lateinit var userNameTextView:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewElements(view)
        initViewModel()
        observeViewModel()
    }

    private fun initViewElements(view:View){

        signOutButton = view.findViewById(R.id.signoutBtn)
        deleteUserButton = view.findViewById(R.id.deleteUserBtn)
        userNameTextView = view.findViewById(R.id.usernameTV)

        userNameTextView.text = LoginState.user?.username

        deleteUserButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun observeViewModel(){
        viewModel.signout.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,getString(R.string.signedOut),Toast.LENGTH_SHORT).show()
            goToSignUpScreen()
        })
        viewModel.userDeleted.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,getString(R.string.user_deleted),Toast.LENGTH_SHORT).show()
            goToSignUpScreen()
        })
    }

    private fun goToSignUpScreen(){
        view?.let {
            val action = MainFragmentDirections.actionGoToSignup()
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.signoutBtn -> onSignout()
            R.id.deleteUserBtn -> onDelete()
        }
    }



    private fun onSignout() {
        viewModel.onSignout()
    }

    private fun onDelete() {
        activity?.let {
            AlertDialog.Builder(it).setTitle(R.string.delete_user)
                .setMessage(getString(R.string.are_you_sure_you_want_to_delete_the_user))
                .setPositiveButton(getString(R.string.yes)){p0,p1-> viewModel.onDeleteUser()}
                .setNegativeButton(getString(R.string.cancel),null)
                .create()
                .show()
        }

        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(usernameTV).navigate(action)
    }
}
