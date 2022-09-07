package com.samsul.capstoneproject.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.samsul.capstoneproject.detail.fragment.follower.FollowerFragment
import com.samsul.capstoneproject.detail.fragment.following.FollowingFragment

class SectionPagerAdapter(
    private val count: Int,
    fm: FragmentManager,
    lifeCycle: Lifecycle,
    data: Bundle
) : FragmentStateAdapter(fm, lifeCycle) {

    private var fragmentBundle = data

    override fun getItemCount(): Int = count

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle

        return fragment as Fragment
    }
}