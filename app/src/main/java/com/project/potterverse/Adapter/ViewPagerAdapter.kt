package com.project.potterverse.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.potterverse.views.fragments.homeFragment
import com.project.potterverse.views.fragments.subFragments.BooksFragment
import com.project.potterverse.views.fragments.subFragments.CharactersFragment
import com.project.potterverse.views.fragments.subFragments.MoviesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> homeFragment()
            1 -> CharactersFragment()
            2 -> MoviesFragment()
            3 -> BooksFragment()
            else -> homeFragment()
        }
    }

}