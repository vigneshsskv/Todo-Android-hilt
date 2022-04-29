package com.vignesh.todo.common.repository.local

import android.content.SharedPreferences
import javax.inject.Inject

sealed interface TDSharePreferenceImpl {
    var autherticationToken: String?
    var refreshToken: String?
}

class TDSharePreference @Inject constructor(preference: SharedPreferences) : TDSharePreferenceImpl {
    override var autherticationToken: String?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var refreshToken: String?
        get() = TODO("Not yet implemented")
        set(value) {}

}