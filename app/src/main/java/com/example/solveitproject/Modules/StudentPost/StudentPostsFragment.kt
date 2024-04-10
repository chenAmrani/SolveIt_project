package com.example.solveitproject.Modules.StudentPost

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Models.StudentPostModel
import com.example.solveitproject.Modules.Posts.AllPostsFragmentDirections
import com.example.solveitproject.Modules.StudentPost.StudentPostAdapter.StudentPostRecyclerAdapter
import com.example.solveitproject.ProfileFragmentDirections
import com.example.solveitproject.R
import com.example.solveitproject.databinding.FragmentStudentPostBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


import com.google.firebase.auth.FirebaseAuth

class StudentPostsFragment : Fragment() {

    private val args: StudentPostsFragmentArgs by navArgs()

    var StudentPostsRecyclerView: RecyclerView? = null
    var adapter: StudentPostRecyclerAdapter? = null
    var progressBar: ProgressBar? = null

    private var _binding: FragmentStudentPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StudentPostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val studentID = args.userId

        _binding = FragmentStudentPostBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[StudentPostsViewModel::class.java]

        progressBar = binding.progressBar2

        progressBar?.visibility = View.VISIBLE


        viewModel.studentposts = StudentPostModel.instance.getAllstudentPosts("")
        val liveData: LiveData<MutableList<StudentPost>> =
            StudentPostModel.instance.getAllstudentPosts("")
        val owner: LifecycleOwner = viewLifecycleOwner
        liveData.observe(owner, Observer { studentPosts ->
            studentPosts?.let {
                val iterator = it.iterator()
                while (iterator.hasNext()) {
                    val post = iterator.next()
                    if (post.publisher != studentID) {
                        iterator.remove()
                    }
                }
            }
        })


        val btnMoveToProfile: Button = binding.btnMoveToProfile
        btnMoveToProfile.setOnClickListener {
            StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) {
                val email = it?.email
                val action =
                    StudentPostsFragmentDirections.actionStudentPostsFragmentToProfileFragment(
                        email!!,
                        ""
                    )
                Navigation.findNavController(view).navigate(action)
            }
        }



        StudentPostsRecyclerView = binding.rvStudentPostFragmentList
        StudentPostsRecyclerView?.setHasFixedSize(true)
        StudentPostsRecyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = StudentPostRecyclerAdapter((viewModel.studentposts?.value))
        adapter?.listener = object : StudentPostRecyclerViewActivity.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "personPostsRecyclerAdapter: Position clicked $position")
                val personPost = viewModel.studentposts?.value?.get(position)
                personPost?.let {
                    val action =
                        StudentPostsFragmentDirections.actionStudentPostsFragmentToStudentSpecificPostFragment(
                            it.postid
                        )
                    Navigation.findNavController(view).navigate(action)
                }
            }

            override fun onStudentPostClicked(studentposts: StudentPost?) {
                Log.i("TAG", "Student Post $studentposts")
            }
        }

        StudentPostsRecyclerView?.adapter = adapter

        viewModel.studentposts?.observe(viewLifecycleOwner) {
            adapter?.studentposts = it
            adapter?.notifyDataSetChanged()
            progressBar?.visibility = View.GONE
        }

        binding.pullToRefresh2.setOnRefreshListener {
            reloadData()
        }
        StudentPostModel.instance.studentPostsListLoadingState.observe(viewLifecycleOwner) { state ->
            binding.pullToRefresh2.isRefreshing = state == StudentPostModel.LoadingState.LOADING
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                            StudentPostsFragmentDirections.actionStudentPostsFragmentToAllPostsFragment()
                        Navigation.findNavController(view).navigate(action)
                        true
                    }

                    R.id.addPostFragment -> {
                        // Navigate to the add post fragment
                        val action =
                            StudentPostsFragmentDirections.actionStudentPostsFragmentToAddStudentPostFragment(
                                studentEmail
                            )
                        Navigation.findNavController(view).navigate(action)
                        true
                    }
                    R.id.profileFragment -> {
                        // Navigate to the add post fragment
                        val action =
                            StudentPostsFragmentDirections.actionStudentPostsFragmentToProfileFragment(
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

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    private fun reloadData() {
        progressBar?.visibility = View.VISIBLE
        StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) {
            StudentPostModel.instance.refreshAllstudentPosts("")
            // }
            progressBar?.visibility = View.GONE
        }
    }

        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }


    }
