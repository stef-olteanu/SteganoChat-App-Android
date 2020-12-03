package com.stefanolteanu.steganochatapp.Network

import io.reactivex.Observable
import com.google.gson.JsonObject
import com.stefanolteanu.steganochatapp.Models.UserIdentity
import com.stefanolteanu.steganochatapp.Network.UserAPI.Companion.API
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface UserAPI {
    @POST(API)
    @FormUrlEncoded
    fun enrollUser(@Field("user") user : UserIdentity): Observable<UserIdentity>

    companion object {
        const val API = "users/enrollNewUser"

        private var api: UserAPI? = null

        operator fun invoke(): UserAPI {
            if(api == null) {
                api = BaseAPI.invoke().create(UserAPI::class.java)
            }
            return api!!
        }
    }
}