package com.project.potterverse.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.potterverse.data.datasource.PotterLocalDatasource
import com.project.potterverse.data.datasource.PotterRemoteDataSource
import com.project.potterverse.data.db.AppDatabase
import com.project.potterverse.data.repository.PotterRepository
import com.project.potterverse.data.service.RetrofitInstance
import com.project.potterverse.view.Adapter.SearchItemAdapter
import com.project.potterverse.databinding.FragmentSearchBinding
import com.project.potterverse.utils.Constant
import com.project.potterverse.view.viewModel.SearchViewModel
import com.project.potterverse.view.activities.BookDetailsActivity
import com.project.potterverse.view.activities.CharacterDetailsActivity
import com.project.potterverse.view.activities.MovieDetailsActivity
import com.project.potterverse.view.viewModel.FilterType
import com.project.potterverse.view.viewModel.SearchItem
import com.project.potterverse.view.viewModel.SearchViewModelFactory


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var itemAdapter: SearchItemAdapter
    lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val potterRepository = PotterRepository(
            remoteDataSource = PotterRemoteDataSource(
                RetrofitInstance.api
            ),
            localDataSource = PotterLocalDatasource(
                AppDatabase.getInstance(requireContext())
            )
        )
        val viewModelFactory = SearchViewModelFactory(potterRepository = potterRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        itemAdapter = SearchItemAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //adapter
        binding.itemResultRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = itemAdapter
        }
        binding.itemResultRecycler.visibility = View.GONE

        binding.searchbar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchAll(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchAll(it)
                }
                return true
            }
        })

        binding.allItemChip.isChecked = true

        binding.allItemChip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setFilter(FilterType.ALL)
        }
        binding.movieChip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setFilter(FilterType.MOVIES)
        }
        binding.characterChip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setFilter(FilterType.CHARACTERS)
        }
        binding.bookChip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setFilter(FilterType.BOOKS)
        }

        itemObserver()
        OnItemClicked()
    }

    //observer
    private fun itemObserver() {
        viewModel.observeItemListLiveData().observe(viewLifecycleOwner) { item ->
            binding.itemResultRecycler.visibility = View.VISIBLE
            itemAdapter.setItems(item as List<SearchItem>)
        }
    }

    //OnItemClick
    private fun OnItemClicked() {
        itemAdapter.onItemClick = { selectedItem ->
            when (selectedItem) {
                is SearchItem.Book -> {
                    val intent = Intent(activity, BookDetailsActivity::class.java)
                    intent.putExtra(Constant.bookID, selectedItem.data.id)
                    intent.putExtra(Constant.bookAuthor, selectedItem.data.attributes.author)
                    intent.putExtra(Constant.bookImage, selectedItem.data.attributes.cover)
                    intent.putExtra(
                        Constant.bookDate,
                        selectedItem.data.attributes.release_date
                    )
                    intent.putExtra(Constant.bookTitle, selectedItem.data.attributes.title)
                    intent.putExtra(
                        Constant.bookChapter,
                        selectedItem.data.relationships.chapters.data.size.toString()
                    )
                    startActivity(intent)
                }

                is SearchItem.Movie -> {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)
                    intent.putExtra(Constant.movieID, selectedItem.data.id)
                    intent.putExtra(Constant.movieTitle, selectedItem.data.attributes.title)
                    intent.putExtra(Constant.movieImage, selectedItem.data.attributes.poster)
                    intent.putExtra(
                        Constant.movieDate,
                        selectedItem.data.attributes.release_date
                    )
                    intent.putExtra(Constant.movieRating, selectedItem.data.attributes.rating)
                    intent.putExtra(Constant.movieBo, selectedItem.data.attributes.box_office)
                    intent.putExtra(
                        Constant.movieDirector,
                        selectedItem.data.attributes.directors!![0]
                    )
                    startActivity(intent)
                }

                is SearchItem.Character -> {
                    val intent = Intent(activity, CharacterDetailsActivity::class.java)
                    intent.putExtra(Constant.chrId, selectedItem.data.id)
                    intent.putExtra(Constant.chrName, selectedItem.data.attributes.name)
                    intent.putExtra(Constant.chrImage, selectedItem.data.attributes.image)
                    intent.putExtra(Constant.chrSpecies, selectedItem.data.attributes.species)
                    intent.putExtra(Constant.chrGender, selectedItem.data.attributes.gender)

                    startActivity(intent)
                }
            }

        }
    }
}

