package com.stefanolteanu.steganochatapp.Activities

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.stefanolteanu.steganochatapp.Models.UserIdentity
import com.stefanolteanu.steganochatapp.R
import com.stefanolteanu.steganochatapp.ViewModels.UserViewModel
import com.stefanolteanu.steganochatapp.databinding.ActivityEnrollPhoneNumberBinding

class EnrollPhoneNumberActivity : AppCompatActivity() {
    private lateinit var activityEnrollPhoneNumberBinding: ActivityEnrollPhoneNumberBinding
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutInflater = LayoutInflater.from(this)
        activityEnrollPhoneNumberBinding = ActivityEnrollPhoneNumberBinding.inflate(layoutInflater)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val mgr: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mgr.hideSoftInputFromWindow(activityEnrollPhoneNumberBinding.phoneNumberEditText.windowToken, 0)
        }
        userViewModel = UserViewModel()
        activityEnrollPhoneNumberBinding.enrollUser.setOnClickListener(enrollUser())

        setContentView(activityEnrollPhoneNumberBinding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_rigth
        )
    }
    
    private fun enrollUser() : View.OnClickListener {
        return View.OnClickListener { 
            val phoneNumber = activityEnrollPhoneNumberBinding.phoneNumberEditText.text.toString()
            if(phoneNumber.isEmpty()) {
                activityEnrollPhoneNumberBinding.phoneNumberEditText.error = "Phone number cannot be empty"
                return@OnClickListener
            }
            if(phoneNumber.length != 10) {
                activityEnrollPhoneNumberBinding.phoneNumberEditText.error = "Phone number must have 10 digits"
                return@OnClickListener
            }
            val userIdentity = UserIdentity()
            userIdentity.phoneNumber = phoneNumber
            userViewModel!!.enrollUser(userIdentity)
        }
    }
}