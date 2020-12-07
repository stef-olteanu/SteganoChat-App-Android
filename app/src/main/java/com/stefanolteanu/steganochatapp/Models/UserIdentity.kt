package com.stefanolteanu.steganochatapp.Models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.stefanolteanu.steganochatapp.Utils.Util


data class UserIdentity(
    @SerializedName("phoneNumber")
    val phoneNumber: String)
{
    @SerializedName("deviceId")
    var deviceId : String = Util.getDeviceId()

    fun toJson() : JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("phoneNumber",phoneNumber)
        jsonObject.addProperty("deviceId",deviceId)
        return jsonObject
    }

}