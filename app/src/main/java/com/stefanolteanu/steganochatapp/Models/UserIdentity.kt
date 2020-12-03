package com.stefanolteanu.steganochatapp.Models

import com.stefanolteanu.steganochatapp.Utils.Util

class UserIdentity {

    var deviceId : String
    lateinit var phoneNumber: String

    init {
        this.deviceId = Util.getDeviceId()
    }
}