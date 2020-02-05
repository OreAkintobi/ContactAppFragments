package com.ore.contactapp

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/** SHOWS WHERE CONTACT APP IS CREATED AND VARIABLES INITIALIZED FOR DATABASES **/
@Entity(tableName = "contacts_table")
@Parcelize
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone") val phone: String?
) : Parcelable
