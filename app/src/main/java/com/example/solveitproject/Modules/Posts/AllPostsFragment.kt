package com.example.solveitproject.Modules.AllPostsFragment

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
import com.example.solveitproject.Model.StudentModel
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Models.StudentPostModel
import com.example.solveitproject.Modules.Posts.PostAdapter.PostsRecyclerAdapter
import com.example.solveitproject.Modules.Posts.PostViewModel
import com.example.solveitproject.Modules.Posts.PostsRecyclerViewActivity
import com.example.solveitproject.databinding.FragmentAllpostsBinding




class AllPostsFragment : Fragment() {
    var PostsRcyclerView: RecyclerView? = null
    var adapter: PostsRecyclerAdapter? = null
    var progressBar: ProgressBar? = null

    private var _binding: FragmentAllpostsBinding? = null
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

        viewModel.posts = StudentPostModel.instance.getAllstudentPosts("")



        PostsRcyclerView = binding.rvAllPostFragmentList
        PostsRcyclerView?.setHasFixedSize(true)
        PostsRcyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = PostsRecyclerAdapter((viewModel.posts?.value))
        adapter?.listener = object : PostsRecyclerViewActivity.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "AllPostsRecyclerAdapter: Position clicked $position")
                val AllPost = viewModel.posts?.value?.get(position)
                AllPost?.let {
//                    val action = AllPostsFragmentDirections.actionAllPostsFragmentToPersonSpecificPostFragment(it.postid)
//                    Navigation.findNavController(view).navigate(action)
                }
            }

            override fun onPostClicked(studentpost: StudentPost?) {
                // Implement the logic here
                Log.i("TAG", "Post Clicked: $studentpost")
                // You can add any other logic you need here
            }

        }

        PostsRcyclerView?.adapter = adapter

//        viewModel.studentposts?.observe(viewLifecycleOwner) {
//            adapter?.studentposts = it
//            adapter?.notifyDataSetChanged()
//            progressBar?.visibility = View.GONE
//        }

        binding.pullToRefresh.setOnRefreshListener {
            reloadData()
        }
//            StudentPostModel.instance.StudentPostsListLoadingState.observe(viewLifecycleOwner) { state ->
//            binding.pullToRefresh.isRefreshing = state == StudentPostModel.LoadingState.LOADING
//        }
//
//        val myPostsButton: Button = binding.btnMyPosts
//        myPostsButton.setOnClickListener {
//            StudentModel.instance.getStudent(FirebaseAuth.getInstance().currentUser?.uid!!) {
//                val personId= it?.id
//                val action = AllPostsFragmentDirections.actionGeneralPostsFragmentToPersonPostsFragment(personId!!)
//                Navigation.findNavController(view).navigate(action)
//            }
//        }
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