package com.example.solveitproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Modules.Posts.AllPostsFragmentDirections
import com.example.solveitproject.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private val args: ProfileFragmentArgs by navArgs()

    private lateinit var viewModel: StudentViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as MainActivity).setBottomBarVisibility(true)
        (requireActivity() as MainActivity).setAddMenuItemVisibility(true)
        super.onViewCreated(view, savedInstanceState)
        val imageView2: ImageView = view.findViewById(R.id.imageView2)

        val email = args.email
        var userId= args.userId

        viewModel.student= StudentModel.instance.getStudentByEmail(email) { student ->
            if (student != null) {
                view.findViewById<TextView>(R.id.nameTextView).text = "Name: ${student.name}"
                view.findViewById<TextView>(R.id.emailTextView).text = "Email: ${student.email}"
                userId = student.id

                Picasso.get().load(student.image)
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageView2)
            } else {
                Log.e("ProfileFragment", "Student details not found for email: $email")
                view.findViewById<TextView>(R.id.emailTextView).text = "Email: Not found"
                view.findViewById<TextView>(R.id.nameTextView).text = "Name: Not found"
            }

        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            logoutUser()
        }

        view.findViewById<Button>(R.id.editButton).setOnClickListener {
            Log.i("ProfileFragment", "MoveToEdit-userId: $userId")
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(email,userId)
            Navigation.findNavController(view).navigate(action)
        }
        val myPostsButton: Button = binding.moveToMyPostsButton
        myPostsButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToStudentPostsFragment(email,userId)
            Navigation.findNavController(view).navigate(action)

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
                            ProfileFragmentDirections.actionProfileFragmentToAllPostsFragment()
                        Navigation.findNavController(view).navigate(action)
                        true
                    }

                    R.id.addPostFragment -> {
                        // Navigate to the add post fragment
                        val action =
                            AllPostsFragmentDirections.actionAllPostsFragmentToAddStudentPostFragment(
                                studentEmail
                            )
                        Navigation.findNavController(view).navigate(action)
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun logoutUser() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_logInFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
