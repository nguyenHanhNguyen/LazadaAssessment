package com.lazada.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazada.core.DataHolder
import com.lazada.core.Failure
import com.lazada.domain.GetUserUseCase
import com.lazada.model.user.UserDomain
import com.lazada.model.user.UserView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val _userProfile = MutableLiveData<UserView>()
    val userProfile: LiveData<UserView> = _userProfile
    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading
    private val _networkError = MutableLiveData<Boolean>()
    val networkError = _networkError
    private val _featureError = MutableLiveData<Boolean>()
    val featureError = _featureError
    private val _generalError = MutableLiveData<Boolean>()
    val generalError = _generalError

    fun getUserInfo(userName: String) {
        _loading.postValue(true)
        viewModelScope.launch {
            when (val result = getUserUseCase(userName)) {
                is DataHolder.Success<*> -> onGetUserSuccess(result.response as UserDomain)
                is DataHolder.Error -> onGetUserFail(result.failure)
            }
        }
    }

    private fun onGetUserSuccess(userDomain: UserDomain) {
        _loading.postValue(false)
        val userView = userDomain.toUserView()
        _userProfile.postValue(userView)
    }

    private fun onGetUserFail(failure: Failure) {
        _loading.postValue(false)
        when (failure) {
            is Failure.FeatureFailure -> featureError.postValue(true)
            is Failure.GeneralFailure -> generalError.postValue(true)
            is Failure.NetworkConnection -> networkError.postValue(true)
        }
    }

}