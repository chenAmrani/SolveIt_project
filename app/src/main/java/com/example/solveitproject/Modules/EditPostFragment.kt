package com.example.solveitproject.Modules

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Models.StudentPostModel
import com.example.solveitproject.Modules.EditPostFragmentArgs
import com.example.solveitproject.Modules.EditPostFragmentDirections
import com.example.solveitproject.Modules.addStudentPost.ImageSelectionAdapter
import com.example.solveitproject.PostSpecificFragmentDirections
import com.example.solveitproject.ProfileFragmentDirections
import com.example.solveitproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class EditPostFragment : Fragment() {

    private val args: EditPostFragmentArgs by navArgs()

    private lateinit var editTextCourse: EditText
    private lateinit var editTextTopic: EditText
    private lateinit var editTextAddionalText: EditText
    private lateinit var buttonCancel: Button
    private lateinit var buttonDeletePost: Button
    private lateinit var buttonSelectImage: Button
    private lateinit var buttonSave: Button
    private lateinit var imageView: ImageView
    private var publisher: String? = null
    private var currentImageUrl: String? = null


    private val PICK_IMAGE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)
        setupUI(view)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = args.postId

        ImagePreloadingTask().execute()

        StudentPostModel.instance.getStudentPostById(postId) {
            editTextCourse.setText(it?.course)
            editTextTopic.setText(it?.topic)
            editTextAddionalText.setText(it?.additionalText)
            publisher = it?.publisher
            Picasso.get().load(it?.image).into(imageView)
            currentImageUrl = it?.image
        }

        buttonCancel.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        buttonDeletePost.setOnClickListener {

            StudentPostModel.instance.getStudentPostById(postId) {
                if (it != null) {
                    StudentPostModel.instance.deleteStudentPost(it) {}
                }
            }
            val action = EditPostFragmentDirections.actionEditPostFragmentToAllPostsFragment()
            Navigation.findNavController(view).navigate(action)
        }
        buttonSave.setOnClickListener {
            //val postid = editTextPostId.text.toString()
            val course = editTextTopic.text.toString()
            val topic = editTextAddionalText.text.toString()
            val additionalText = editTextCourse.text.toString()

            if ( course.isNullOrBlank() || topic.isNullOrBlank() || additionalText.isNullOrBlank()) {
                // Show an error message to the user, for example, using a Toast
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {

                Log.i("EditPostFragment", "currentImageUrl: $currentImageUrl")

                val updatedPersonPost = StudentPost(postId, publisher, additionalText, course, topic,currentImageUrl!!)

                StudentPostModel.instance.updateStudentPost(updatedPersonPost) {
                }
                val action = EditPostFragmentDirections.actionEditPostFragmentToAllPostsFragment()
                Navigation.findNavController(view).navigate(action)
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
                            EditPostFragmentDirections.actionEditPostFragmentToAllPostsFragment()
                        Navigation.findNavController(view).navigate(action)
                        true
                    }

                    R.id.addPostFragment -> {
                        // Navigate to the add post fragment
                        val action =
                            EditPostFragmentDirections.actionEditPostFragmentToAddStudentPostFragment(
                                studentEmail
                            )
                        Navigation.findNavController(view).navigate(action)
                        true
                    }

                    R.id.profileFragment -> {
                        // Navigate to profile
                        val action =
                            EditPostFragmentDirections.actionEditPostFragmentToProfileFragment(
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

    private fun setupUI(view: View) {
        editTextCourse = view.findViewById(R.id.editTextCourse)
        editTextTopic = view.findViewById(R.id.editTextTopic)
        editTextAddionalText = view.findViewById(R.id.editTextAdditionalText)
        buttonCancel = view.findViewById(R.id.buttonCancel) // Corrected ID
        buttonDeletePost = view.findViewById(R.id.buttonDeletePost)
        buttonSelectImage = view.findViewById(R.id.btnSelectImage)
        buttonSave = view.findViewById(R.id.buttonSave)
        imageView = view.findViewById(R.id.imageView)
//        val imageView1: ImageView = view.findViewById(R.id.imageView1)



        buttonSelectImage.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_image_selection, null)
            val recyclerViewImages: RecyclerView = dialogView.findViewById(R.id.recyclerViewImages)

            recyclerViewImages.layoutManager = GridLayoutManager(requireContext(), 2)


            val imageUrls = listOf(
                "https://cdn.education.com/cdn-cgi/image/width=320/files/static/exercises/screenshots/screenshot-exercise-partial-products-small.png",
                "https://i.imagesup.co/images2/2f039076a62fd802e2424ad2bf70a5ab5bab0316.jpg",
                "https://i.imagesup.co/images2/0__05c0386463498d.jpg",
                "https://i.imagesup.co/images2/0__05d7b9801e2b5a.jpg",
                "https://i.imagesup.co/images2/c1c2aa2c0d0ea07f02f1e54487b4b3d0d798e40a.png",
                "https://i.imagesup.co/images2/86a586505d6c4ee3da4d628cf05179f681a10b56.png",
                "https://images.slideplayer.com/12/3406264/slides/slide_3.jpg",
                "https://i.ytimg.com/vi/WInk-m_6l1c/maxresdefault.jpg",
                "https://i.imagesup.co/images2/6df7b33bc83eca7b12d14783cd462f48177502f6.png",
                "https://images.slideplayer.com/12/3406264/slides/slide_9.jpg",
                "https://images.slideplayer.com/12/3406264/slides/slide_6.jpg",
                "https://images.slideplayer.com/12/3406264/slides/slide_13.jpg",
                "https://i.imagesup.co/images2/a92902c515962eeee8c4ebec8c231ea489435e58.png",
                "https://i.imagesup.co/images2/7f0be64988a41b18daa0f3468bd3e1ba40c558a3.jpg"            )


            val adapter = ImageSelectionAdapter(imageUrls) { imageUrl ->
                currentImageUrl = imageUrl
                Picasso.get().load(imageUrl)
                    .resize(600, 600)
                    .centerCrop()
                    .into(imageView)
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

    private fun saveImageToGallery(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }


        // Insert the image into MediaStore
        val uri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        // Write the bitmap data to the content resolver
        uri?.let { imageUri ->
            requireContext().contentResolver.openOutputStream(imageUri).use { outputStream ->
                outputStream?.let {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
            }
            Toast.makeText(requireContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            Picasso.get().load(selectedImageUri).into(imageView)
        }
    }

    inner class ImagePreloadingTask : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            preloadImages()
        }

        private fun preloadImages() {
            val imageUrls = listOf(
                "https://cdn.education.com/cdn-cgi/image/width=320/files/static/exercises/screenshots/screenshot-exercise-partial-products-small.png",
                "https://i.imagesup.co/images2/2f039076a62fd802e2424ad2bf70a5ab5bab0316.jpg",
                "https://i.imagesup.co/images2/0__05c0386463498d.jpg",
                "https://i.imagesup.co/images2/0__05d7b9801e2b5a.jpg",
                "https://i.imagesup.co/images2/c1c2aa2c0d0ea07f02f1e54487b4b3d0d798e40a.png",
                "https://i.imagesup.co/images2/86a586505d6c4ee3da4d628cf05179f681a10b56.png",
                "https://images.slideplayer.com/12/3406264/slides/slide_3.jpg",
                "https://i.ytimg.com/vi/WInk-m_6l1c/maxresdefault.jpg",
                "https://i.imagesup.co/images2/6df7b33bc83eca7b12d14783cd462f48177502f6.png",
                "https://images.slideplayer.com/12/3406264/slides/slide_9.jpg",
                "https://images.slideplayer.com/12/3406264/slides/slide_6.jpg",
                "https://images.slideplayer.com/12/3406264/slides/slide_13.jpg",
                "https://i.imagesup.co/images2/a92902c515962eeee8c4ebec8c231ea489435e58.png",
                "https://i.imagesup.co/images2/7f0be64988a41b18daa0f3468bd3e1ba40c558a3.jpg"            )

            for (imageUrl in imageUrls) {
                Picasso.get().load(imageUrl).fetch()
            }
        }
    }

    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
    }
}