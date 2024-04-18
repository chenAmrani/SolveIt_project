package com.example.solveitproject.Modules.Posts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.GoogleMapsApiActivity
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Models.PostModel
import com.example.solveitproject.Models.StudentPostModel
import com.example.solveitproject.Modules.Posts.PostAdapter.PostsRecyclerAdapter
import com.example.solveitproject.R
import com.example.solveitproject.databinding.FragmentAllpostsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class AllPostsFragment : Fragment() {
    var PostsRecyclerView: RecyclerView? = null
    var adapter: PostsRecyclerAdapter? = null
    var progressBar: ProgressBar? = null
    var myPostsButton: Button? = null


    private var _binding: FragmentAllpostsBinding?= null
    private val binding get() = _binding!!

    private lateinit var viewModel: PostViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAllpostsBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[PostViewModel::class.java]

        progressBar = binding.progressBar

        progressBar?.visibility = View.VISIBLE

        viewModel.posts = StudentPostModel.instance.getAllstudentPosts( "")


        PostsRecyclerView = binding.rvAllPostFragmentList
        PostsRecyclerView?.setHasFixedSize(true)
        PostsRecyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = PostsRecyclerAdapter((viewModel.posts?.value))
        myPostsButton = binding.btnMyPosts


        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.mainActivityBottomNavigationView)
        StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) { it ->
            val studentId = it?.id.toString()
            val studentEmail = it?.email.toString()
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profileFragment -> {
                        // Navigate to the user profile fragment
                        val action =
                            AllPostsFragmentDirections.actionAllPostsFragmentToProfileFragment(
                                studentEmail,
                                studentId
                            )
                        Navigation.findNavController(view).navigate(action)
                        true // Return true to indicate that the item has been handled
                    }
                    R.id.addPostFragment -> {
                        // Navigate to the add post fragment
                        val action = AllPostsFragmentDirections.actionAllPostsFragmentToAddStudentPostFragment(studentEmail)
                        Navigation.findNavController(view).navigate(action)
                        true // Return true to indicate that the item has been handled
                    }
                    else -> false // Return false if the item click is not handled
                }
            }


            adapter?.listener = object : PostsRecyclerViewActivity.OnItemClickListener {

                override fun onItemClick(position: Int) {
                    Log.i("TAG", "PostsRecyclerAdapter: Position clicked $position")
                    val post = viewModel.posts?.value?.get(position)
                    post?.let {
                    val action = AllPostsFragmentDirections.actionAllPostsFragmentToStudentSpecificPostFragment(
                        it.postid
                    )
                    Navigation.findNavController(view).navigate(action)
                    }
                }

                override fun onPostClicked(posts: StudentPost?) {
                    Log.i("TAG", "Post $posts")
                }
            }


            PostsRecyclerView?.adapter = adapter

            viewModel.posts?.observe(viewLifecycleOwner) {
                adapter?.posts = it
                adapter?.notifyDataSetChanged()
                progressBar?.visibility = View.GONE
            }

            binding.pullToRefresh.setOnRefreshListener {
                reloadData()
            }
            StudentPostModel.instance.studentPostsListLoadingState.observe(viewLifecycleOwner) { state ->
                binding.pullToRefresh.isRefreshing = state == StudentPostModel.LoadingState.LOADING
            }

            val myPostsButton: Button = binding.btnMyPosts

            myPostsButton.setOnClickListener {
                StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) {
                    val studentEmail = it?.email.toString()
                    val userId = it?.id.toString()
                    if (studentEmail != null) {
                        val action = AllPostsFragmentDirections.actionAllPostsFragmentToStudentPostsFragment(studentEmail,userId)
                        Navigation.findNavController(view).navigate(action)
                    }
                }
            }
            val mapButton: Button = binding.btnMaps
            mapButton.setOnClickListener {
                    val intent = Intent(requireContext(), GoogleMapsApiActivity::class.java)
                    startActivity(intent)
                }
            }

            return view
        }


    override fun onResume() {
        super.onResume()
        reloadData()
    }

    private fun reloadData() {
        progressBar?.visibility = View.VISIBLE
        StudentPostModel.instance.refreshAllstudentPosts("")
        progressBar?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}