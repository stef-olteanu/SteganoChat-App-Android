package com.stefanolteanu.steganochatapp.ViewModels

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stefanolteanu.steganochatapp.Models.UserIdentity
import com.stefanolteanu.steganochatapp.Network.UserAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class UserViewModel: ViewModel() {

    companion object {
        val TAG = "USER_VIEW_MODEL"
    }

    private val networkCallSuccess = MutableLiveData<Boolean>()
    private val userData = MutableLiveData<UserIdentity>()

    fun enrollUser(user : UserIdentity) = UserAPI.invoke().enrollUser(user.toJson())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe( {
            Log.e(TAG,"Success")
        }, { error ->
            if( error is NetworkErrorException) {
                Log.e(TAG,"There is a network error: " + error.localizedMessage)
            }
        })

    fun updateDeviceForPhoneNumber(user : UserIdentity) = UserAPI.invoke().updateDeviceForPhoneNumber(user.toJson())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe( {
            Log.e(TAG,"Success")
        }, { error ->
            if( error is NetworkErrorException) {
                Log.e(TAG,"There is a network error: " + error.localizedMessage)
            }
        })

    fun getUserByPhoneNumber(phoneNumber : String) = UserAPI.invoke().getUserByPhoneNumber(phoneNumber)
        .subscribeOn(Schedulers.io())
        .subscribeBy(
            onSuccess = { userIdentity ->
                userData.postValue(userIdentity)
                networkCallSuccess.postValue(true)
            },
            onError = { e ->
                networkCallSuccess.postValue(false)
                if (e is NetworkErrorException) {
                    Log.e("Network error", e.localizedMessage)
                }
            }
            )


    fun getNetworkCallSuccess() : LiveData<Boolean> = networkCallSuccess

    fun getUserIdentity() : LiveData<UserIdentity> = userData
}