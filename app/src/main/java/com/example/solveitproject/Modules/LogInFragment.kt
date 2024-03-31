package com.example.solveitproject.Modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.solveitproject.R


class LogInFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var signInButton: Button? = null
    private  var registerButton: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view : View) {
        emailTextField = view.findViewById(R.id.emailTextField)
        passwordTextField = view.findViewById(R.id.passwordTextField)
        signInButton = view.findViewById(R.id.BtnSignIn)
        registerButton = view.findViewById(R.id.ButtonRegister)


        registerButton?.setOnClickListener{
            val action = LogInFragmentDirections.actionLogInFragmentToRegisterFragment()
            Navigation.findNavController(view).navigate(action)
        }

        signInButton?.setOnClickListener{
            val email = emailTextField?.text.toString().trim()
            val password = passwordTextField?.text.toString().trim()

//            if (email.isEmpty() || password.isEmpty() ) {
//                Toast.makeText(
//                    requireContext(),
//                    "Please fill in all fields",
//                    Toast.LENGTH_SHORT
//                ).show()
////                user.delete()
//            }
        }




    }
}





