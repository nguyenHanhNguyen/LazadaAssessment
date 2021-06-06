package com.lazada.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
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
        viewModel.loading.observe(
            viewLifecycleOwner, {
                renderLoadingState(it)
            }
        )
        viewModel.followerList.observe(
            viewLifecycleOwner, {
                renderFollowersList(it)
            }
        )
        viewModel.networkError.observe(
            viewLifecycleOwner, {
                renderNetworkError(it)
            }
        )
        viewModel.featureError.observe(
            viewLifecycleOwner, {
                renderFeatureError(it)
            }
        )
        viewModel.generalError.observe(
            viewLifecycleOwner, {
                renderGeneralError(it)
            }
        )
    }

    private fun renderNetworkError(error: Boolean) {
        renderError(error, "Please connect to network and try again", mViewBinding.tvError)

    }

    private fun renderFeatureError(error: Boolean) {
        renderError(error, "User not found", mViewBinding.tvError)

    }

    private fun renderGeneralError(error: Boolean) {
        renderError(error, "Something went wrong", mViewBinding.tvError)
    }

    private fun renderError(error: Boolean, msg: String, errorTv: TextView) {
        if (error) {
            mViewBinding.rvFollowers.visibility = View.GONE
            errorTv.visibility = VISIBLE
            errorTv.text = msg
        } else {
            errorTv.visibility = View.GONE
        }
    }

    private fun renderLoadingState(loading: Boolean) {
        if (loading) {
            mViewBinding.loading.visibility = VISIBLE
        } else {
            mViewBinding.loading.visibility = View.GONE
        }
    }


    private fun renderFollowersList(followerList: MutableList<FollowersView>) {
        mViewBinding.rvFollowers.visibility = VISIBLE
        mAdapter.followers.clear()
        mAdapter.followers.addAll(followerList)
        mAdapter.notifyDataSetChanged()
    }

}