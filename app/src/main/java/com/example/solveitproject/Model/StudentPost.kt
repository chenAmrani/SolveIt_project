package com.example.solveitproject.Model


import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.solveitproject.base.MyApplication

//import com.google.firebase.Timestamp
//import com.google.firebase.firestore.FieldValue

    @Entity
    data class StudentPost(
        @PrimaryKey val postid: String,
        val publisher: String?, //id of the student who posted
        val image: String,
        var lastUpdated: Long? = null
    )
    {
        companion object {

            var lastUpdated: Long
                get()
                {
                    return MyApplication.Globals
                        .appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                        ?.getLong(GET_LAST_UPDATED, 0) ?: 0
                }
                set(value)
                {
                    MyApplication.Globals
                        .appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                        ?.edit()
                        ?.putLong(GET_LAST_UPDATED, value)
                        ?.apply()
                }

            @PrimaryKey
            const val id_KEY = "postid"
            const val PUBLISHER_KEY = "postpublisher"
            const val IMAGE_KEY = "postimage"
            const val LAST_UPDATED = "lastUpdated"
            const val GET_LAST_UPDATED = "get_last_updated"

            fun fromJSON(json: Map<String, Any>): StudentPost? {
                val postid = json[id_KEY] as? String ?: ""
                val publisher = json[id_KEY] as? String ?: ""
                val image = json[IMAGE_KEY] as? String ?: ""
                val studentPost= StudentPost(postid,publisher, image)


//                val timestamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
//                timestamp?.let {
//                    studntonPost.lastUpdated = it.seconds
//                }

                return studentPost
            }
        }


        val json: Map<String, Any>
            get() {
                return hashMapOf(
                    id_KEY to postid,
                    IMAGE_KEY to image ,
                    PUBLISHER_KEY to publisher!!

                )

            }
    }









