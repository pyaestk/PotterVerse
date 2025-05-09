package com.project.potterverse.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.project.potterverse.R
import com.project.potterverse.data.datasource.PotterLocalDatasource
import com.project.potterverse.data.datasource.PotterRemoteDataSource
import com.project.potterverse.data.db.AppDatabase
import com.project.potterverse.data.repository.PotterRepository
import com.project.potterverse.data.service.RetrofitInstance
import com.project.potterverse.view.Adapter.ViewPagerAdapter
import com.project.potterverse.databinding.ActivityMainBinding
import com.project.potterverse.view.viewModel.MainViewModel
import com.project.potterverse.view.viewModel.MainViewModelFactory
import com.project.potterverse.view.fragments.FavoritesFragment
import com.project.potterverse.view.fragments.SearchFragment
import com.project.potterverse.view.fragments.homeFragment
import com.project.potterverse.view.fragments.subFragments.BooksFragment
import com.project.potterverse.view.fragments.subFragments.CharactersFragment
import com.project.potterverse.view.fragments.subFragments.MoviesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var homeFragment: homeFragment
    private lateinit var charactersFragment: CharactersFragment
    private lateinit var moviesFragment: MoviesFragment
    private lateinit var booksFragment: BooksFragment
    
    private lateinit var searchFragment: SearchFragment
    private lateinit var favoritesFragment: FavoritesFragment

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by lazy {

        val potterRepository = PotterRepository(
            remoteDataSource = PotterRemoteDataSource(
                RetrofitInstance.api
            ),
            localDataSource = PotterLocalDatasource(
                AppDatabase.getInstance(this)
            )
        )
        val mainViewModelFactory = MainViewModelFactory(potterRepository)
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

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

            .commit()
    }

    private fun initializeFragments() {
        homeFragment = homeFragment()
        charactersFragment = CharactersFragment()
        moviesFragment = MoviesFragment()
        booksFragment = BooksFragment()

        searchFragment = SearchFragment()
        favoritesFragment = FavoritesFragment()
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

            .show(fragment).commitNow()

        binding.viewPager2.visibility = if (fragment == searchFragment) {
            View.GONE
        } else if (fragment == favoritesFragment) {
            View.GONE
        } else {
            View.VISIBLE
        }

        binding.frameLayout.visibility = if (fragment == searchFragment) {
            View.VISIBLE
        } else if (fragment == favoritesFragment) {
            View.VISIBLE
        }else {
            View.GONE
        }

    }




}
