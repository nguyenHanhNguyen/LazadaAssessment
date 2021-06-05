package com.lazada.ui.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lazada.databinding.FollowerItemBinding
import com.lazada.model.follower.FollowersView

class FollowersAdapter : RecyclerView.Adapter<ViewHolder>() {

    var followers = mutableListOf<FollowersView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(FollowerItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(followers[position])
    }

    override fun getItemCount(): Int = followers.size

}

class ViewHolder(private val followerItemBinding: FollowerItemBinding) :
    RecyclerView.ViewHolder(followerItemBinding.root) {

    fun bind(data: FollowersView) {
        followerItemBinding.tvLogin.text = data.login
        followerItemBinding.imvAvatar.load(data.avatarUrl)
    }

}