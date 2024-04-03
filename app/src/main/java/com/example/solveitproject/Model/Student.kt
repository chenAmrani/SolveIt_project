package com.example.solveitproject.Model

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Student(
    @PrimaryKey val name: String,
    @PrimaryKey val email: String,
    val password: String,
    val id: String,
    val avatarUrl: String,
    var isChecked: Boolean

)
