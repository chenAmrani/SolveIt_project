package com.example.solveitproject.Modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.solveitproject.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.solveitproject.Model.Student
import com.example.solveitproject.Model.StudentModel


class RegisterFragment : Fragment() {

    private var nameTextField: EditText? = null
    private var idTextField: EditText? = null
    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null
    private var uploadImageButton: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view : View) {
        nameTextField = view.findViewById(R.id.nameTextField)
        idTextField = view.findViewById(R.id.idTextField)
        emailTextField = view.findViewById(R.id.emailTextField)
        passwordTextField = view.findViewById(R.id.passwordTextField)
        saveButton = view.findViewById(R.id.BtnRegisterDone)
        cancelButton = view.findViewById(R.id.BtnRegisterCancel)
        uploadImageButton = view.findViewById(R.id.BtnUploadImage)


        cancelButton?.setOnClickListener{
            Navigation.findNavController(it).popBackStack()
        }

        saveButton?.setOnClickListener{
            val name = nameTextField?.text.toString()
            val id = idTextField?.text.toString()
            val email = emailTextField?.text.toString().trim()
            val password = passwordTextField?.text.toString().trim()

            if (name.isEmpty() || id.isEmpty() || email.isEmpty() || password.isEmpty() ) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
//                user.delete()
            }

//

        }

    }
}



