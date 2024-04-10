package com.example.solveitproject.Modules

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.solveitproject.R
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.MainActivity
import com.example.solveitproject.Model.Student
import com.example.solveitproject.Modules.addStudentPost.ImageSelectionAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.UUID


class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var nameTextField: EditText? = null
    private var idTextField: EditText? = null
    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null
    private var uploadImageButton: Button? = null
    private var imageView: ImageView? = null
    var currentImageUrl: String?= null
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
        imageView = view.findViewById(R.id.imageView)

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        uploadImageButton?.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_image_selection, null)
            val recyclerViewImages: RecyclerView = dialogView.findViewById(R.id.recyclerViewImages)

            recyclerViewImages.layoutManager = GridLayoutManager(requireContext(), 4)

            val imageUrls = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Labrador_Retriever_portrait.jpg/1200px-Labrador_Retriever_portrait.jpg",
            )

            val adapter = ImageSelectionAdapter(imageUrls) { imageUrl ->
                currentImageUrl = imageUrl
                Picasso.get().load(imageUrl).into(imageView)


            }
            recyclerViewImages.adapter = adapter

            AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Select Image")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            auth = FirebaseAuth.getInstance()
            ImagePreloadingTask().execute()

            saveButton?.setOnClickListener {
                var filled= true
                val email = emailTextField?.text.toString().trim()
                val password = passwordTextField?.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                registerUser(email, password)
            }
        }

    inner class ImagePreloadingTask : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            preloadImages()
        }
        private fun preloadImages() {
            val imageUrls = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Labrador_Retriever_portrait.jpg/1200px-Labrador_Retriever_portrait.jpg",
            )

            for (imageUrl in imageUrls) {
                Picasso.get().load(imageUrl).fetch()
            }
        }
    }





    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        var name = nameTextField?.text.toString()
                        var id = idTextField?.text.toString()


                        if (name.isNullOrBlank() || id.isNullOrBlank() || currentImageUrl.isNullOrEmpty()) {

                            Toast.makeText(
                                requireContext(),
                                "Please fill in all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                            user.delete()

                        } else {
                            val student = Student(name, id, email, currentImageUrl!!)
                            val db = FirebaseFirestore.getInstance()
                            db.collection("students")
                                .document(userId)
                                .set(student)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Registration successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        context,
                                        "Error adding document: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_RegisterFragment_to_allPostsFragment)
                            (requireActivity() as MainActivity).setBottomBarVisibility(true)
                            (requireActivity() as MainActivity).setAddMenuItemVisibility(true)

                        }
                    } else {
                        Toast.makeText(context, "User is null", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(context, "Email is already in use", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Registration failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    }

                }
            }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            currentImageUrl = selectedImageUri.toString() // Save the selected image URL
            Picasso.get().load(selectedImageUri).into(imageView) // Display the selected image
        }
    }


    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
    }
}






