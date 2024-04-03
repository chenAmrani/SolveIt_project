package com.example.solveitproject.Modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.example.solveitproject.R
import com.example.solveitproject.Model.Post
import com.example.solveitproject.Models.PostModel


class AddPostFragment : Fragment() {

    private var curseTextField: EditText? = null
    private var topicTextField: EditText? = null
    private var takenFromTextFiled: EditText? = null
    private var FullNameTextField: EditText? = null
    private var AdditionalTextField: EditText? = null
    private var addPostButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view:View) {
        curseTextField = view.findViewById(R.id.CurseTextField)
        topicTextField = view.findViewById(R.id.TopicTextField)
        takenFromTextFiled = view.findViewById(R.id.TakenFromTextField)
        FullNameTextField = view.findViewById(R.id.FirstNameLastNameTextField)
        AdditionalTextField = view.findViewById(R.id.AdditionalTextField)
        addPostButton = view.findViewById(R.id.BtnAddNewPost)




        addPostButton?.setOnClickListener {
            val curseName = curseTextField?.text.toString()
            val topic = topicTextField?.text.toString()
            val takenFrom = takenFromTextFiled?.text.toString()
            val Publishername = FullNameTextField?.text.toString()
            val additonalText = AdditionalTextField?.text.toString()
            val post = Post(curseName,topic,takenFrom,Publishername)

            PostModel.instance.addPost(post){
        }

            Navigation.findNavController(view).navigate(R.id.allPostsFragment)
        }


    }
}
