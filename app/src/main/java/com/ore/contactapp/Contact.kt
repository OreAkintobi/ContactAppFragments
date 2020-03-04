package com.ore.contactapp

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/** SHOWS WHERE CONTACT APP IS CREATED AND VARIABLES INITIALIZED FOR DATABASES **/
@Parcelize
@Entity(tableName = "contact_table")
data class Contact(
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "phone")
    val phone: String?
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0L
    //    @IgnoredOnParcel
    @ColumnInfo(name = "contactDbId")
    var contactDbId: String? = ""
}
