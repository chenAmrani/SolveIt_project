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
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Models.StudentPostModel
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        var currentStudentPost: StudentPost? = null
        Log.i("TAG", " StudentSpecificPostFragment: Post ID ${postId}")
        val textViewCourse: TextView = view.findViewById(R.id.textViewCourse)
        val textViewTopic: TextView = view.findViewById(R.id.textViewTopic)
        val textViewAdditionalText: TextView = view.findViewById(R.id.additionalTextLabelTextView)
        val imageView: ImageView = view.findViewById(R.id.imageViewPost)
        var publisher: String? = null

        StudentPostModel.instance.getStudentPostById(postId) {
            currentStudentPost = it
            textViewCourse.text = it?.course
            textViewTopic.text = it?.topic
            publisher = it?.publisher
            textViewAdditionalText.text = it?.additionalText
            if (!it?.image.isNullOrEmpty()) {
                Picasso.get().load(it?.image)
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageView)
            }
        }
        StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) {
            Log.i("TAG", " StudentSpecificPostFragment: Student ID ${it?.id}")
            Log.i("TAG", " StudentSpecificPostFragment: Student publisher ${publisher}")

            if (it?.id == publisher) {
                Log.i("TAG", " StudentSpecificPostFragment: Student ID ${it?.id}")
                Log.i("TAG", " StudentSpecificPostFragment: Student publisher ${publisher}")
                val editButton: Button = view.findViewById(R.id.buttonEdit)
                editButton.visibility = View.VISIBLE
                val deleteButton: Button = view.findViewById(R.id.buttonDelete)
                deleteButton.visibility = View.VISIBLE
                editButton.setOnClickListener {
                    val action =
                        StudentSpecificPostFragmentDirections.actionStudentSpecificPostFragmentToEditPostFragment(
                            postId
                        )
                    Navigation.findNavController(view).navigate(action)
                }
                deleteButton.setOnClickListener {
                    StudentPostModel.instance.deleteStudentPost(currentStudentPost!!) {
                        // Navigate back to the AllPostsFragment
                        val action = StudentSpecificPostFragmentDirections.actionStudentSpecificPostFragmentToAllPostsFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                }
            } else {
                val editButton: Button = view.findViewById(R.id.buttonDelete)
                editButton.visibility = View.GONE
                val deleteButton: Button = view.findViewById(R.id.buttonDelete)
                deleteButton.visibility = View.GONE
            }
        }


        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.mainActivityBottomNavigationView)
        StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) {
            val studentId = it?.id.toString()
            val studentEmail = it?.email.toString()
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.allPostsFragment -> {
                        // Navigate to the user allPosts fragment
                        val action =
                            StudentSpecificPostFragmentDirections.actionStudentSpecificPostFragmentToAllPostsFragment()
                        Navigation.findNavController(view).navigate(action)
                        true
                    }

                    R.id.addPostFragment -> {
                        // Navigate to the add post fragment
                        val action =
                            StudentSpecificPostFragmentDirections.actionStudentSpecificPostFragmentToAddStudentPostFragment(
                                studentEmail
                            )
                        Navigation.findNavController(view).navigate(action)
                        true
                    }
                    R.id.profileFragment -> {
                        // Navigate to profile
                        val action =
                            StudentSpecificPostFragmentDirections.actionStudentSpecificPostFragmentToProfileFragment(
                                studentEmail,
                                studentId
                            )
                        Navigation.findNavController(view).navigate(action)
                        true
                    }

                    else -> false
                }
            }
    }
}
    }
