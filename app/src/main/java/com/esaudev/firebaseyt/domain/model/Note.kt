package com.esaudev.firebaseyt.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Note (
    val id: String = UUID.randomUUID().toString(),
    val note: String = "",
    val author: String = ""
        ): Parcelable