package com.stefanolteanu.steganochatapp.Activities

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.stefanolteanu.steganochatapp.R
import com.stefanolteanu.steganochatapp.Utils.Util
import com.stefanolteanu.steganochatapp.ViewModels.UserViewModel
import com.stefanolteanu.steganochatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding : ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private var arePermissionsGranted = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = UserViewModel()
        checkAndRequestPermissions()
        if(arePermissionsGranted) {
            checkIfNumberIsAlreadyEnrolled()
        }
        val layoutInflater = LayoutInflater.from(this)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        activityMainBinding.continueButton.setOnClickListener {
            val intent = Intent(this,
                EnrollPhoneNumberActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
        setContentView(activityMainBinding.root)
    }


    private fun checkAndRequestPermissions() {
        val readPhoneStatePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_PHONE_STATE
        )
        val readSMSPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_SMS
        )
        val readPhoneNumberPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_PHONE_NUMBERS
        )
        val permissionList = ArrayList<String>()
        if(readPhoneStatePermission != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE)
        if(readSMSPermission != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.READ_SMS)
        if(readPhoneNumberPermission != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.READ_PHONE_NUMBERS)
        if(permissionList.isNotEmpty()) {
            arePermissionsGranted = false
            ActivityCompat.requestPermissions(this,permissionList.toTypedArray(),1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.size < 3) {
            return
        } else {

        }
    }

    private fun checkIfNumberIsAlreadyEnrolled() {
        userViewModel.getUserByPhoneNumber(Util.getDevicePhoneNumber())
        userViewModel.getUserIdentity().observe(this, Observer {userIdentity ->
            if(userIdentity.deviceId == Util.getDeviceId()) {
                val intent = Intent(this,HomeChatActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                finish()
            }
        })
    }
}