package com.example.solveitproject.Modules.addStudentPost

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Models.StudentPostModel
import com.example.solveitproject.R
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import java.util.UUID

class addStudentPostFragment : Fragment() {

    private val REQUEST_CODE_STORAGE_PERMISSION = 456

    private lateinit var editTextCurseName: EditText
    private lateinit var editTextTopic: EditText
    private lateinit var editTextAdditionalText: EditText
    private lateinit var btnPost: Button
    private lateinit var btnCancel: Button
    private lateinit var btnUploadImage: Button
    private lateinit var imageView: ImageView
    var currentImageUrl: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_student_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        editTextCurseName = view.findViewById(R.id.editTextCourseName)
        editTextTopic = view.findViewById(R.id.editTextTopicName)
        editTextAdditionalText = view.findViewById(R.id.editTextAdditonalText)
        btnPost = view.findViewById(R.id.btnPost)
        btnCancel = view.findViewById(R.id.btnCancel)
        btnUploadImage = view.findViewById(R.id.btnUploadImage)
        imageView = view.findViewById(R.id.imageView)

        btnUploadImage.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_image_selection, null)
            val recyclerViewImages: RecyclerView = dialogView.findViewById(R.id.recyclerViewImages)

            recyclerViewImages.layoutManager = GridLayoutManager(requireContext(), 4)

            val imageUrls = listOf(
            "https://cdn.education.com/cdn-cgi/image/width=320/files/static/exercises/screenshots/screenshot-exercise-partial-products-small.png"
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

        btnPost.setOnClickListener {
            val course = editTextCurseName.text.toString()
            val topic = editTextTopic.text.toString()
            val additionalText = editTextAdditionalText.text.toString()

            if (course.isNullOrBlank() || topic.isNullOrBlank() || additionalText.isNullOrBlank()  || currentImageUrl.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) { user ->
                    val ID: String = UUID.randomUUID().toString()
                    val publisher = user?.id.toString()

                    val studentPost = StudentPost(ID, publisher, course, topic, additionalText, currentImageUrl!!)
                    StudentPostModel.instance.addStudentPost(user?.email!!, studentPost) {}


                    val action = addStudentPostFragmentDirections.actionAddStudentPostFragmentToAllPostsFragment()
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }



        btnCancel.setOnClickListener {
            val action = addStudentPostFragmentDirections.actionAddStudentPostFragmentToAllPostsFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            Picasso.get().load(selectedImageUri).into(imageView)
        }
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImagePreloadingTask().execute()
    }

    inner class ImagePreloadingTask : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            preloadImages()
        }

        private fun preloadImages() {
            val imageUrls = listOf(
                "https://cdn.education.com/cdn-cgi/image/width=320/files/static/exercises/screenshots/screenshot-exercise-partial-products-small.png"
            )

            for (imageUrl in imageUrls) {
                Picasso.get().load(imageUrl).fetch()
            }
        }
    }

    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
    }
}
