package com.example.solveitproject.Modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.solveitproject.R
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.solveitproject.Model.Student
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Models.StudentPostModel
import com.example.solveitproject.Modules.addStudentPost.addStudentPostFragment
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class StudentSpecificPostFragment : Fragment() {

    private val args: StudentSpecificPostFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_specific, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = args.postId
        Log.i("TAG", " PersonSpecificPostFragment: Post ID $postId")
        val textViewRequest: TextView = view.findViewById(R.id.textViewCourse)
        val textViewOffer: TextView = view.findViewById(R.id.textViewTopic)
        val textViewAdditionalText: TextView = view.findViewById(R.id.textViewAdditionalText)
        val imageView: ImageView = view.findViewById(R.id.imageViewPost)

        StudentPostModel.instance.getStudentPostById(postId){
            textViewRequest.text = it?.course
            textViewOffer.text = it?.topic
            textViewAdditionalText.text = it?.additionalText
            if (!it?.image.isNullOrEmpty()) {
                Picasso.get().load(it?.image)
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageView)
            }
            StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!){
                Log.i("TAG", " PersonSpecificPostFragment: Person ID ${it?.id}")
                    val editButton: Button = view.findViewById(R.id.buttonEdit)
                    editButton.visibility = View.VISIBLE  // Set the visibility to visible

                    editButton.setOnClickListener {
                        val action =
//                            addStudentPostFragment.actionPersonSpecificPostFragmentToEditPostFragment(
                                postId
//                            )
//                        Navigation.findNavController(view).navigate(action)
                    }
//                } else {
//                    val editButton: Button = view.findViewById(R.id.buttonEdit)
//                    editButton.visibility = View.GONE  // Set the visibility to gone
//                }
            }
        }

    }



}

