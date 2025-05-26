package com.example.foodpanda.model

import android.os.Parcel
import android.os.Parcelable

data class Menu(
    var name: String? = null,
    var price: Float = 0f,
    var totalInCart: Int = 0,
    var url: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeFloat(price)
        parcel.writeInt(totalInCart)
        parcel.writeString(url)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Menu> {
        override fun createFromParcel(parcel: Parcel): Menu = Menu(parcel)
        override fun newArray(size: Int): Array<Menu?> = arrayOfNulls(size)
    }
}
