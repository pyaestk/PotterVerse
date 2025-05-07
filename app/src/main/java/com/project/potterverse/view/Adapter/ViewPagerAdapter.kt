package com.project.potterverse.view.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.potterverse.view.fragments.homeFragment
import com.project.potterverse.view.fragments.subFragments.BooksFragment
import com.project.potterverse.view.fragments.subFragments.CharactersFragment
import com.project.potterverse.view.fragments.subFragments.MoviesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> homeFragment()
            1 -> MoviesFragment()
            2 -> BooksFragment()
            3 -> CharactersFragment()
            else -> homeFragment()
        }
    }

}