package uk.co.jamiecruwys.apieceofcake.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cake(
    val title: String?,
    val desc: String?,
    val image: String?
) : Parcelable