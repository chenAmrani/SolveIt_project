package com.example.solveitproject.Model


import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.solveitproject.base.MyApplication
import com.google.protobuf.Timestamp


@Entity
    data class StudentPost(
    @PrimaryKey val postid: String,
    val publisher: String?,
    val course: String?,
    val topic: String?,
    val additionalText: String?,
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
            const val COURSE_NAME_KEY = "courseName"
            const val TOPIC_NAME = "topicName"
            const val ADDIONAL_TEXT = "addionalText"
            const val IMAGE_KEY = "postimage"
            const val LAST_UPDATED = "lastUpdated"
            const val GET_LAST_UPDATED = "get_last_updated"

            fun fromJSON(json: Map<String, Any>): StudentPost? {
                val postid = json[id_KEY] as? String ?: ""
                val publisher = json[PUBLISHER_KEY] as? String ?: ""
                val course = json[COURSE_NAME_KEY] as? String ?: ""
                val topic = json[TOPIC_NAME] as? String ?: ""
                val additionalText = json[ADDIONAL_TEXT] as? String ?: ""
                val image = json[IMAGE_KEY] as? String ?: ""
                val studentPost= StudentPost(postid,publisher,course,topic,additionalText, image)


                val timestamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
                timestamp?.let {
                    studentPost.lastUpdated = it.seconds
                }
                return studentPost
            }
        }


        val json: Map<String, Any>
            get() {
                return hashMapOf<String, Any>(
                    id_KEY to postid!!,
                    PUBLISHER_KEY to publisher!!,
                    COURSE_NAME_KEY to course!!,
                    TOPIC_NAME to topic!!,
                    ADDIONAL_TEXT to additionalText!!,
                    IMAGE_KEY to image!!
                )
            }


    }









