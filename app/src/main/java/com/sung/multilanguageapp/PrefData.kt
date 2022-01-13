package com.sung.multilanguageapp

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor


class PrefData {
    var pref: SharedPreferences? = null
    var editor: Editor? = null
    private var _context: Context? = null

    // Shared pref mode
    private var PRIVATE_MODE = 0

    constructor(context: Context?) {
        _context = context
        pref = _context?.getSharedPreferences(Constants.PREF_NAME, PRIVATE_MODE)
        editor = pref!!.edit()
    }

    fun setCurrentLanguage(language: String?) {
        editor!!.putString(Constants.PREF_LANG, language)
        editor!!.commit()
    }

    fun getCurrentLanguage(): String? {
        return pref!!.getString(Constants.PREF_LANG, "eng")
    }
}