package com.danigutiadan.foodreminder

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.google.gson.Gson
import javax.inject.Inject

class Preferences @Inject constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, 0)


    var user: UserInfo?
        get() = Gson().fromJson(sharedPreferences.getString(context.getString(R.string.user_info), null), UserInfo::class.java)
        set(value) = sharedPreferences.edit { putString(context.getString(R.string.user_info), Gson().toJson(value)) }

//    var additionalUserInfo: AdditionalUserInfo?
//        get() = Gson().fromJson(sharedPreferences.getString(context.getString(R.string.additional_user_info), null), AdditionalUserInfo::class.java)
//        set(value) = sharedPreferences.edit { putString(context.getString(R.string.additional_user_info), Gson().toJson(value)) }

//    var authCredential: AuthCredential?
//        get() = Gson().fromJson(sharedPreferences.getString(context.getString(R.string.auth_credential), null), AuthCredential::class.java)
//        set(value) = sharedPreferences.edit { putString(context.getString(R.string.auth_credential), Gson().toJson(value)) }



}