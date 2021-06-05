package com.lazada.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import com.lazada.R
import com.lazada.databinding.FragmentUserBinding
import com.lazada.model.user.UserView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var mViewBinding: FragmentUserBinding
    private var mUserName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = FragmentUserBinding.inflate(inflater, container, false)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpSearchBtn()
        setUpUserListener()
        setUpNavigation()
    }

    private fun setUpSearchBtn() {
        mViewBinding.btnSearch.setOnClickListener {
            val searchQuery = mViewBinding.edSearch.text.toString()
            if (searchQuery.isNotEmpty()) {
                mUserName = searchQuery
                viewModel.getUserInfo(searchQuery)
            }
        }
    }

    private fun setUpUserListener() {
        viewModel.loading.observe(
            viewLifecycleOwner, {
                renderLoadingState(it)
            }
        )
        viewModel.userProfile.observe(
            viewLifecycleOwner, {
                renderUserProfile(it)
            }
        )
    }

    private fun renderUserProfile(userView: UserView) {
        mViewBinding.content.visibility = VISIBLE
        mViewBinding.tvName.text = userView.name
        mViewBinding.tvTwitter.text = userView.twitterUsername
        mViewBinding.tvCompany.text = userView.company
        mViewBinding.tvLocation.text = userView.location
        mViewBinding.tvFollowers.text = userView.followers.toString()
        mViewBinding.imvAvatar.load(userView.avatarUrl)
    }

    private fun renderLoadingState(loading: Boolean) {
        if (loading) {
            mViewBinding.loadding.visibility = VISIBLE
        } else {
            mViewBinding.loadding.visibility = GONE
        }
    }

    private fun setUpNavigation() {
        mViewBinding.btnShowFollowers.setOnClickListener { view ->
            val action = UserFragmentDirections.actionUserFragmentToFollowersFragment(mUserName)
            view.findNavController().navigate(action)
        }
    }

}