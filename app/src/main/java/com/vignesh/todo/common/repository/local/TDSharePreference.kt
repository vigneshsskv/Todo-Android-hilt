package com.vignesh.todo.common.repository.local

import android.content.SharedPreferences
import javax.inject.Inject

sealed interface LNSharePreferenceImpl {
    var autherticationToken: String?
    var refreshToken: String?
}

class LNSharePreference @Inject constructor(preference: SharedPreferences) : LNSharePreferenceImpl {
    override var autherticationToken: String?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var refreshToken: String?
        get() = TODO("Not yet implemented")
        set(value) {}

}