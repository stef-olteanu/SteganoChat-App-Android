package com.stefanolteanu.steganochatapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.stefanolteanu.steganochatapp.R
import com.stefanolteanu.steganochatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}