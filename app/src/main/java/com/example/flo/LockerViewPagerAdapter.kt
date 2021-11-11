package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }
}