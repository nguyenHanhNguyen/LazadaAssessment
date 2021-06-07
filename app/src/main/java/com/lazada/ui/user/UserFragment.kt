package com.lazada.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
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
        renderError(error, getString(R.string.network_error), mViewBinding.tvError)

    }

    private fun renderFeatureError(error: Boolean) {
        renderError(error, getString(R.string.user_not_found_error), mViewBinding.tvError)
    }

    private fun renderGeneralError(error: Boolean) {
        renderError(error, getString(R.string.general_error), mViewBinding.tvError)
    }

    private fun renderError(error: Boolean, msg: String, errorTv: TextView) {
        if (error) {
            mViewBinding.content.visibility = GONE
            errorTv.visibility = VISIBLE
            errorTv.text = msg
        } else {
            errorTv.visibility = GONE
        }
    }

    private fun renderUserProfile(userView: UserView) {
        mViewBinding.content.visibility = VISIBLE
        mViewBinding.tvError.visibility = GONE
        mViewBinding.tvName.text = userView.name
        mViewBinding.tvLogin.text = userView.login
        mViewBinding.tvTwitter.text = userView.twitterUsername
        mViewBinding.tvCompany.text = userView.company
        mViewBinding.tvLocation.text = userView.location
        mViewBinding.tvFollowers.text = userView.followers.toString()
        mViewBinding.imvAvatar.load(userView.avatarUrl)
    }

    private fun renderLoadingState(loading: Boolean) {
        if (loading) {
            mViewBinding.loading.visibility = VISIBLE
            mViewBinding.content.visibility = GONE
        } else {
            mViewBinding.loading.visibility = GONE
            mViewBinding.content.visibility = VISIBLE
        }
    }

    private fun setUpNavigation() {
        mViewBinding.btnShowFollowers.setOnClickListener { view ->
            val action =
                UserFragmentDirections.actionUserFragmentToFollowersFragment(mViewBinding.edSearch.text.toString())
            view.findNavController().navigate(action)
        }
    }

}