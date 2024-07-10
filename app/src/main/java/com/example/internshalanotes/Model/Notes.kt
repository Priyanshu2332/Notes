package com.example.internshalanotes.Model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String,
    var subtitle: String,
    var notes: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeString(notes)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notes> {
        override fun createFromParcel(parcel: Parcel): Notes {
            return Notes(parcel)
        }

        override fun newArray(size: Int): Array<Notes?> {
            return arrayOfNulls(size)
        }
    }
}
