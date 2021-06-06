package com.lazada.ui.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazada.core.DataHolder
import com.lazada.core.Failure
import com.lazada.domain.GetUserFollowersUserCase
import com.lazada.model.follower.FollowersDomain
import com.lazada.model.follower.FollowersView
import com.lazada.model.user.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(val followersUserCase: GetUserFollowersUserCase) :
    ViewModel() {

    private val _followerList = MutableLiveData<MutableList<FollowersView>>()
    val followerList: LiveData<MutableList<FollowersView>> = _followerList

    fun getFollowers(userName: String) {
        viewModelScope.launch {
            when (val result = followersUserCase(userName)) {
                is DataHolder.Success<*> -> onGetUserSuccess(result.response as List<FollowersDomain>)
                is DataHolder.Error -> onGetUserFail(result.failure)
            }
        }
    }

    private fun onGetUserSuccess(followersDomain: List<FollowersDomain>) {
        val followersViewList = mutableListOf<FollowersView>()
        followersDomain.forEach {
            followersViewList.add(it.toFollowersView())
        }
        _followerList.postValue(followersViewList)
    }

    private fun onGetUserFail(failure: Failure) {
        when (failure) {
            is Failure.FeatureFailure -> TODO()
            Failure.GeneralFailure -> TODO()
            Failure.NetworkConnection -> TODO()
        }
    }

}