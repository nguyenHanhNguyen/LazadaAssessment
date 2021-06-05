package com.lazada.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazada.databinding.FragmentFollowersBinding
import com.lazada.model.follower.FollowersView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersFragment : Fragment() {

    private val viewModel: FollowersViewModel by viewModels()
    private lateinit var mViewBinding: FragmentFollowersBinding
    private var mAdapter = FollowersAdapter()
    private val args: FollowersFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = FragmentFollowersBinding.inflate(inflater, container, false)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = args.userName
        viewModel.getFollowers(userName)
        mViewBinding.rvFollowers.layoutManager = LinearLayoutManager(context)
        mViewBinding.rvFollowers.adapter = mAdapter
        setUpFollowersListener()
    }

    private fun setUpFollowersListener() {
        viewModel.followerList.observe(
            viewLifecycleOwner, {
                renderFollowersList(it)
            }
        )
    }

    private fun renderFollowersList(followerList: MutableList<FollowersView>) {
        mAdapter.followers.clear()
        mAdapter.followers.addAll(followerList)
        mAdapter.notifyDataSetChanged()
    }

}