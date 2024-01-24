package com.project.potterverse.views.fragments.subFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.potterverse.Adapter.BaseCharacterAdapter
import com.project.potterverse.R
import com.project.potterverse.data.CharactersData
import com.project.potterverse.databinding.FragmentCharactersBinding
import com.project.potterverse.viewModel.MainViewModel
import com.project.potterverse.views.MainActivity
import com.project.potterverse.views.activities.CharacterDetailsActivity
import com.project.potterverse.views.fragments.homeFragment


class CharactersFragment : Fragment() {

    lateinit var characterAdapter: BaseCharacterAdapter
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentCharactersBinding
    private var pageNumber = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        characterAdapter = BaseCharacterAdapter(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isLoading = false

        val layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.characterRecycler.layoutManager = layoutManager

        binding.characterRecycler.adapter = characterAdapter

        binding.characterRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                val isLastItemVisible = (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0

                if (!isLoading && isLastItemVisible) {
                    // End of the list reached, load more data
                    isLoading = true
                    pageNumber++
                    viewModel.getCharacters(pageNumber)
                }
            }


        })

        viewModel.getCharacters(pageNumber)
        viewModel.getCharacterListLiveData().observe(viewLifecycleOwner) { character ->
            characterAdapter.addCharacters(character as ArrayList<CharactersData>)
            isLoading = false
        }

        characterAdapter.onItemClick = { chr ->
            val intent = Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra(homeFragment.chrId, chr.id)
            intent.putExtra(homeFragment.chrName, chr.attributes.name)
            intent.putExtra(homeFragment.chrImage, chr.attributes.image)
            intent.putExtra(homeFragment.chrSpecies, chr.attributes.species)
            intent.putExtra(homeFragment.chrGender, chr.attributes.gender)
            startActivity(intent)
        }
    }
    
    
}