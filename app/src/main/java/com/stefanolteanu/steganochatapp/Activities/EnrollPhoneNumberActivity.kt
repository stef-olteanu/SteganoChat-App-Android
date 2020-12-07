package com.stefanolteanu.steganochatapp.Activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stefanolteanu.steganochatapp.Models.UserIdentity
import com.stefanolteanu.steganochatapp.R
import com.stefanolteanu.steganochatapp.Utils.Util
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
            val userIdentityCreated = UserIdentity(phoneNumber)
            Util.saveDevicePhoneNumber(phoneNumber)
            userViewModel!!.getUserByPhoneNumber(Util.getDevicePhoneNumber())
            userViewModel!!.getUserIdentity().observe(this, Observer {userIdentity ->
                if(userIdentity.deviceId == Util.getDeviceId()) {
                    startChatHomeActivity()
                    finish()
                } else if (userIdentity.deviceId != "null" && userIdentity.deviceId != Util.getDeviceId()) {
                    AlertDialog.Builder(this)
                        .setTitle("You are already connected")
                        .setMessage("You are already connected on another device! Do you want to connect on this device? This action will disconnect you from the other.")
                        .setPositiveButton(android.R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                            updatePhoneNumberAndStartNewActivity(userIdentityCreated)
                        }
                        .setNegativeButton(android.R.string.no) { dialogInterface: DialogInterface, i: Int ->
                            finish()
                        }
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                } else if (userIdentity.deviceId == "null") {
                    enrollPhoneNumberAndStartNewActivity(userIdentityCreated)
                }
            })
        }
    }

    public fun enrollPhoneNumberAndStartNewActivity(userIdentity : UserIdentity) {
        userViewModel!!.enrollUser(userIdentity)
        startChatHomeActivity()
    }

    public fun updatePhoneNumberAndStartNewActivity(userIdentity : UserIdentity) {
        userViewModel!!.updateDeviceForPhoneNumber(userIdentity)
        startChatHomeActivity()
    }

    private fun startChatHomeActivity() {
        val intent = Intent(this, HomeChatActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}