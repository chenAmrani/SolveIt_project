package com.example.solveitproject

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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.Student
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Modules.addStudentPost.ImageSelectionAdapter
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso



class EditProfileFragment : Fragment() {

    private val args: EditProfileFragmentArgs by navArgs()

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call the preloading task when the fragment is created
        ImagePreloadingTask().execute()

        //val studentId = args.postId
        val email= args.email

        val studentModel = StudentModel.instance
        var currentImageUrl: String? = null
        val editTextName: TextView = view.findViewById(R.id.editTextName)
        val editTextEmail: TextView = view.findViewById(R.id.editTextEmail)
        val btnUploadImage: Button = view.findViewById(R.id.btnUploadImage)
        val imageView1: ImageView = view.findViewById(R.id.imageView1)
        val buttonUpdate: Button = view.findViewById(R.id.buttonUpdate)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
        imageView = view.findViewById(R.id.imageView)
        var studentId= ""

        StudentModel.instance.getStudentByEmail(email){
            Log.i("EditProfileFragment", "email : ${email}")
            editTextName.text =  it?.name
            Log.i("EditProfileFragment", " editTextName: ${editTextName.text} ")
            editTextEmail.text = it?.email
            studentId= it?.id.toString()
            currentImageUrl = it?.image
            Picasso.get().load(it?.image)
                .resize(400, 400)
                .centerCrop()
                .into(imageView1)
            //Picasso.get().load(it?.image).into(imageView1)
            Log.i("EditProfileFragment", " studentId: ${studentId} ")
        }

        btnUploadImage.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_image_selection, null)
            val recyclerViewImages: RecyclerView = dialogView.findViewById(R.id.recyclerViewImages)

            recyclerViewImages.layoutManager = GridLayoutManager(requireContext(),4)

            val imageUrls = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Labrador_Retriever_portrait.jpg/1200px-Labrador_Retriever_portrait.jpg",
            )

            val adapter = ImageSelectionAdapter(imageUrls) { imageUrl ->
                currentImageUrl = imageUrl
                // Load the selected image directly into the ImageView
                Picasso.get().load(imageUrl).into(imageView)
            }
            recyclerViewImages.adapter = adapter

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged()

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Select Image")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            dialog.show()
        }


        buttonUpdate?.setOnClickListener {
            val id = studentId
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()

            if (name.isNullOrBlank() || email.isNullOrBlank() ) {
                // Show an error message to the user, for example, using a Toast
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val updatedStudent = Student(name, id,email,currentImageUrl!!)

                StudentModel.instance.updateStudent(FirebaseAuth.getInstance().currentUser?.uid!!,updatedStudent) {
                    // Navigation.findNavController(it).popBackStack(R.id.StudentPostsFragment, false)
                }
                StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) {
                    val studentId= it?.id
                    val email = it?.email
                    val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment(email!!,studentId!!)
                    Navigation.findNavController(view).navigate(action)
                }
            }

        }


        buttonCancel?.setOnClickListener {
            val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment(email,studentId)
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        // Define the values to insert into the MediaStore
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val uri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

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

        if (requestCode == EditProfileFragment.IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            Picasso.get().load(selectedImageUri).into(imageView as ImageView)
        }
    }

    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
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
}
