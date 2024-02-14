package com.atya.firstsubmissiongithubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.atya.firstsubmissiongithubuserapp.data.remote.response.ItemsItem
import com.atya.firstsubmissiongithubuserapp.databinding.FragmentFollowBinding
import com.atya.firstsubmissiongithubuserapp.ui.adapter.UserListAdapter
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.UserFollowViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    companion object {
        const val ARG_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = requireActivity().intent.getStringExtra(UserDetailActivity.EXTRA_LOGIN)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollow.layoutManager = layoutManager


        val userFollowViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                UserFollowViewModel::class.java
            )

        userFollowViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        if (position == 1) {
            userFollowViewModel.getUserFollower(username.toString())
            userFollowViewModel.userFollowers.observe(viewLifecycleOwner) {
                setFollowAdapter(it)
            }
        } else {
            userFollowViewModel.getUserFollowing(username.toString())
            userFollowViewModel.userFollowing.observe(viewLifecycleOwner) {
                setFollowAdapter(it)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setFollowAdapter(userList: List<ItemsItem>) {
        val adapter = UserListAdapter()
        adapter.submitList(userList)
        binding.rvFollow.adapter = adapter
    }


}