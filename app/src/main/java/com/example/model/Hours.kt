package com.example.foodpanda.model

import android.os.Parcel
import android.os.Parcelable


data class Hours(
    var Sunday: String? = null,
    var Monday: String? = null,
    var Tuesday: String? = null,
    var Wednesday: String? = null,
    var Thursday: String? = null,
    var Friday: String? = null,
    var Saturday: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Sunday)
        parcel.writeString(Monday)
        parcel.writeString(Tuesday)
        parcel.writeString(Wednesday)
        parcel.writeString(Thursday)
        parcel.writeString(Friday)
        parcel.writeString(Saturday)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Hours> {
        override fun createFromParcel(parcel: Parcel): Hours = Hours(parcel)
        override fun newArray(size: Int): Array<Hours?> = arrayOfNulls(size)
    }
    fun Hours?.getTodaysHours(): String {
        val calendar = java.util.Calendar.getInstance()
        val dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
        return when (dayOfWeek) {
            java.util.Calendar.SUNDAY -> this?.Sunday ?: "Closed"
            java.util.Calendar.MONDAY -> this?.Monday ?: "Closed"
            java.util.Calendar.TUESDAY -> this?.Tuesday ?: "Closed"
            java.util.Calendar.WEDNESDAY -> this?.Wednesday ?: "Closed"
            java.util.Calendar.THURSDAY -> this?.Thursday ?: "Closed"
            java.util.Calendar.FRIDAY -> this?.Friday ?: "Closed"
            java.util.Calendar.SATURDAY -> this?.Saturday ?: "Closed"
            else -> "Closed"
        }
    }

}
