package com.irsyad_19104077.myapplication.Data

import android.os.Parcelable

@Parcelize
data class Quote {
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var category: String? = null,
    var date: String? = null
}

annotation class Parcelize
    : Parcelable