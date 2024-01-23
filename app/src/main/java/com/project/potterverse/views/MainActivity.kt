package com.project.potterverse.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.potterverse.R
import com.google.android.material.tabs.TabLayoutMediator
import com.project.potterverse.Adapter.ViewPagerAdapter
import com.project.potterverse.databinding.ActivityMainBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.viewModel.MainViewModelFactory
import com.project.potterverse.views.fragments.CategoryFragment
import com.project.potterverse.views.fragments.FavoritesFragment
import com.project.potterverse.views.fragments.SearchFragment
import com.project.potterverse.views.fragments.homeFragment
import com.project.potterverse.views.fragments.subFragments.BooksFragment
import com.project.potterverse.views.fragments.subFragments.CharactersFragment
import com.project.potterverse.views.fragments.subFragments.MoviesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var homeFragment: homeFragment
    private lateinit var charactersFragment: CharactersFragment
    private lateinit var moviesFragment: MoviesFragment
    private lateinit var booksFragment: BooksFragment

    private lateinit var categoryFragment: CategoryFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var favoritesFragment: FavoritesFragment

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var binding: ActivityMainBinding

//    val viewModel: MainViewModel by lazy {
//        val mainViewModelFactory = MainViewModelFactory()
//        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeFragments()

        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager2.adapter = viewPagerAdapter
        binding.viewPager2.offscreenPageLimit = 1
        binding.viewPager2.isUserInputEnabled = false


        setUPFragments()

        setTabLayoutWithViewPager()
        setBottomNavigation()
    }

    private fun setUPFragments() {
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, homeFragment).hide(homeFragment)
            .add(R.id.frameLayout, charactersFragment).hide(charactersFragment)
            .add(R.id.frameLayout, moviesFragment).hide(moviesFragment)
            .add(R.id.frameLayout, booksFragment).hide(booksFragment)

            .add(R.id.frameLayout, searchFragment).hide(searchFragment)
            .add(R.id.frameLayout, favoritesFragment).hide(favoritesFragment)
            .add(R.id.frameLayout, categoryFragment).hide(categoryFragment)

            .commit()
    }

    private fun initializeFragments() {
        homeFragment = homeFragment()
        charactersFragment = CharactersFragment()
        moviesFragment = MoviesFragment()
        booksFragment = BooksFragment()

        searchFragment = SearchFragment()
        favoritesFragment = FavoritesFragment()
        categoryFragment = CategoryFragment()
    }

    private fun setTabLayoutWithViewPager() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, pos ->
            tab.text = when(pos) {
                0 -> "All Items"
                1 -> "Movies"
                2 -> "Books"
                3 -> "Characters"
                else -> ""
            }
        }.attach()
    }

    private fun setBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homeFragment -> {
                    showFragment(homeFragment)
                    showTabLayout()
                    true
                }
                R.id.searchFragment -> {
                    showFragment(searchFragment)
                    hideTabLayout()
                    true
                }
                R.id.favoritesFragment -> {
                    showFragment(favoritesFragment)
                    hideTabLayout()
                    true
                }
                R.id.categoryFragment -> {
                    showFragment(categoryFragment)
                    hideTabLayout()
                    true
                }
                else -> false
            }
        }
    }

    private fun hideTabLayout() {
        binding.tabLayout.visibility = View.GONE
    }

    private fun showTabLayout() {
        binding.tabLayout.visibility = View.VISIBLE
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(homeFragment)
            .hide(charactersFragment)
            .hide(moviesFragment)
            .hide(booksFragment)
            .hide(searchFragment)
            .hide(favoritesFragment)
            .hide(categoryFragment)

            .show(fragment).commitNow()

        binding.viewPager2.visibility = if (fragment == searchFragment) {
            View.GONE
        } else if (fragment == favoritesFragment) {
            View.GONE
        } else if (fragment == categoryFragment) {
            View.GONE
        } else {
            View.VISIBLE
        }

        binding.frameLayout.visibility = if (fragment == searchFragment) {
            View.VISIBLE
        } else if (fragment == favoritesFragment) {
            View.VISIBLE
        } else if (fragment == categoryFragment) {
            View.VISIBLE
        } else {
            View.GONE
        }

    }




}
