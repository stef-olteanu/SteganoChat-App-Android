package com.stefanolteanu.steganochatapp.Network

import io.reactivex.Observable
import com.google.gson.JsonObject
import com.stefanolteanu.steganochatapp.Models.UserIdentity
import com.stefanolteanu.steganochatapp.Network.UserAPI.Companion.API
import com.stefanolteanu.steganochatapp.Network.UserAPI.Companion.API_GET_USER
import io.reactivex.Single
import retrofit2.http.*
import java.util.*

interface UserAPI {

    @POST(API)
    fun enrollUser(@Body userIdentityJson : JsonObject): Observable<UserIdentity>

    @GET(API_GET_USER)
    fun getUserByPhoneNumber(@Query(value = "phoneNumber") phoneNumber : String) : Single<UserIdentity>

    @POST(API_UPDATE_DEVICE)
    fun updateDeviceForPhoneNumber(@Body phoneNumber : JsonObject) : Single<UserIdentity>

    companion object {
        const val API = "users/enrollNewUser"

        const val API_GET_USER = "users/getUserByPhoneNumber"

        const val API_UPDATE_DEVICE = "users/updateDeviceForPhoneNumber"

        private var api: UserAPI? = null

        operator fun invoke(): UserAPI {
            if(api == null) {
                api = BaseAPI.invoke().create(UserAPI::class.java)
            }
            return api!!
        }
    }
}