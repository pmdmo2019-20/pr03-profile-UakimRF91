package es.iessaladillo.pedrojoya.profile.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Avatar(val id: Int, val imageResId: Int, val name: String): Parcelable {

}

