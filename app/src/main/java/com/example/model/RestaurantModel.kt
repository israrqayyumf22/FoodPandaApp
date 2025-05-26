package com.example.foodpanda.model

import android.os.Parcel
import android.os.Parcelable

data class RestaurantModel(
    var name: String? = null,
    var address: String? = null,
    var image: String? = null,
    var delivery_charge: Float = 0f,
    var hours: Hours? = null,
    var menus: List<Menu>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readParcelable(Hours::class.java.classLoader),
        parcel.createTypedArrayList(Menu.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(image)
        parcel.writeFloat(delivery_charge)
        parcel.writeParcelable(hours, flags)
        parcel.writeTypedList(menus)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RestaurantModel> {
        override fun createFromParcel(parcel: Parcel): RestaurantModel = RestaurantModel(parcel)
        override fun newArray(size: Int): Array<RestaurantModel?> = arrayOfNulls(size)
    }
}
