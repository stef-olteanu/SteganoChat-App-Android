package com.stefanolteanu.steganochatapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.stefanolteanu.steganochatapp.databinding.ActivityHomeChatBinding

class HomeChatActivity : AppCompatActivity() {
    private lateinit var activityHomeChatBinding: ActivityHomeChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutInflater = LayoutInflater.from(this)
        activityHomeChatBinding = ActivityHomeChatBinding.inflate(layoutInflater)
        setContentView(activityHomeChatBinding.root)
    }
}