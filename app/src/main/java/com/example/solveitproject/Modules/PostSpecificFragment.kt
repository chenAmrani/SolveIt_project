package com.example.solveitproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Modules.Posts.AllPostsFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class PostSpecificFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_specific, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
                        ProfileFragmentDirections.actionProfileFragmentToAllPostsFragment()
                    Navigation.findNavController(view).navigate(action)
                    true
                }

                R.id.addPostFragment -> {
                    // Navigate to the add post fragment
                    val action =
                        PostSpecificFragmentDirections.actionPostSpecificFragmentToAddStudentPostFragment(
                            studentEmail
                        )
                    Navigation.findNavController(view).navigate(action)
                    true
                }
                R.id.profileFragment -> {
                    // Navigate to the add post fragment
                    val action =
                        PostSpecificFragmentDirections.actionPostSpecificFragmentToProfileFragment(
                            studentEmail,
                            studentId
                        )
                    Navigation.findNavController(view).navigate(action)
                    true
                }
                R.id.allPostsFragment -> {
                    // Navigate to the add post fragment
                    val action =
                        PostSpecificFragmentDirections.actionPostSpecificFragmentToAllPostsFragment()
                    Navigation.findNavController(view).navigate(action)
                    true
                }

                else -> false
            }
            }
}

    }}