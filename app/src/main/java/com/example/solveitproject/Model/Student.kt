package com.example.solveitproject.Model

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Student(
    @PrimaryKey val name: String,
    @PrimaryKey val email: String,
    val id: String,
    val avatarUrl: String,

)
{

constructor() : this("", "", "", "")

companion object {

    const val NAME_KEY = "name"
    const val ID_KEY = "id"
    const val EMAIL_KEY = "email"
    const val IMAGE_KEY = "image"
    const val GET_LAST_UPDATED = "get_last_updated"


    fun fromJSON(json: Map<String, Any>): Student {
        val id = json[ID_KEY] as? String ?: ""
        val name = json[NAME_KEY] as? String ?: ""
        val email = json[EMAIL_KEY] as? String ?: ""
        val image = json[StudentPost.IMAGE_KEY] as? String ?: ""
        val student = Student(name, id, email,image)

        return student
    }
}


val json: Map<String, Any>
    get() {
        return hashMapOf(
            ID_KEY to id,
            NAME_KEY to name,
            EMAIL_KEY to email,
            )
    }
}
