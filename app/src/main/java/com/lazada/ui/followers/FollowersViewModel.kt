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
    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading
    private val _networkError = MutableLiveData<Boolean>()
    val networkError = _networkError
    private val _generalError = MutableLiveData<Boolean>()
    val generalError = _generalError
    private val _featureError = MutableLiveData<Boolean>()
    val featureError = _featureError

    fun getFollowers(userName: String) {
        _loading.postValue(true)
        viewModelScope.launch {
            when (val result = followersUserCase(userName)) {
                is DataHolder.Success<*> -> onGetUserSuccess(result.response as List<FollowersDomain>)
                is DataHolder.Error -> onGetUserFail(result.failure)
            }
        }
    }

    private fun onGetUserSuccess(followersDomain: List<FollowersDomain>) {
        _loading.postValue(false)
        val followersViewList = mutableListOf<FollowersView>()
        followersDomain.forEach {
            followersViewList.add(it.toFollowersView())
        }
        _followerList.postValue(followersViewList)
    }

    private fun onGetUserFail(failure: Failure) {
        _loading.postValue(true)
        when (failure) {
            is Failure.FeatureFailure -> _featureError.postValue(true)
            Failure.GeneralFailure -> _generalError.postValue(true)
            Failure.NetworkConnection -> _networkError.postValue(true)
        }
    }

}