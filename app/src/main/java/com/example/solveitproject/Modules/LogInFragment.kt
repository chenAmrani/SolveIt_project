package com.example.solveitproject.Modules

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.solveitproject.MainActivity
import com.example.solveitproject.R
import com.google.firebase.auth.FirebaseAuth


class LogInFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var emailTextField: EditText
    private lateinit var passwordTextField: EditText
    private lateinit var signInButton: Button
    private lateinit var registerButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        emailTextField = view.findViewById(R.id.SignInTextEmailAddress)
        passwordTextField = view.findViewById(R.id.SignInTextPassword)
        signInButton = view.findViewById(R.id.BtnSignIn)
        registerButton = view.findViewById(R.id.ButtonRegister)

        registerButton.setOnClickListener{
            val action = LogInFragmentDirections.actionLogInFragmentToRegisterFragment()
            Navigation.findNavController(view).navigate(action)
        }

        signInButton.setOnClickListener {
            val email = emailTextField.text.toString().trim()
            val password = passwordTextField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Login user
            loginUser(email, password)

        }
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setBottomBarVisibility(false)
        (requireActivity() as MainActivity).setAddMenuItemVisibility(false)
        sharedPreferences = requireActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()

    }


    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Login successful
                    saveLoginCredentials(email, password)
                    navigateToAllPosts()
                    (requireActivity() as MainActivity).setBottomBarVisibility(true)
                    (requireActivity() as MainActivity).setAddMenuItemVisibility(true)

                } else {
                    // Login failed
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToAllPosts() {
        // Retrieve the current user from Firebase Authentication
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val email = user.email
            val userId = user.uid

            // Navigate to the all posts fragment and pass the user's email and user ID as arguments
            val action = LogInFragmentDirections.actionLogInFragmentToAllPostsFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }




    private fun saveLoginCredentials(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }



    }






