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
import com.example.solveitproject.databinding.FragmentProfileBinding
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
        // Fetch user details using the email
        viewModel.student= StudentModel.instance.getStudentByEmail(email) { student ->
            view.findViewById<TextView>(R.id.emailTextView).text = "Email: ${student?.email}"
            view.findViewById<TextView>(R.id.nameTextView).text = "Name: ${student?.name}"
            if (student != null) {
                userId= student.id
            }
            Picasso.get().load(student?.image)
                .resize(400, 400)
                .centerCrop()
                .into(imageView2)

        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            logoutUser()
        }

        view.findViewById<Button>(R.id.editButton).setOnClickListener {
            // Handle navigating to edit profile fragment
            Log.i("ProfileFragment", "MoveToEdit-userId: $userId")
            val action = ProfileFragmentDirections.actionProfileFragmentToEditPostFragment(email)
            Navigation.findNavController(view).navigate(action)
        }
        val myPostsButton: Button = binding.moveToMyPostsButton
        myPostsButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditPostFragment(userId)
            Navigation.findNavController(view).navigate(action)

        }

    }

    private fun logoutUser() {
//        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_entryFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
