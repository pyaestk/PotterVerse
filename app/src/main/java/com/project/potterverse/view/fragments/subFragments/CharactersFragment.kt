package com.project.potterverse.view.fragments.subFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.potterverse.view.Adapter.BaseCharacterAdapter
import com.project.potterverse.model.CharacterDetailsData
import com.project.potterverse.databinding.FragmentCharactersBinding
import com.project.potterverse.utils.Constant
import com.project.potterverse.view.viewModel.MainViewModel
import com.project.potterverse.view.MainActivity
import com.project.potterverse.view.activities.CharacterDetailsActivity


class CharactersFragment : Fragment() {

    lateinit var characterAdapter: BaseCharacterAdapter
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentCharactersBinding
    private var pageNumber = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
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

        showProgressBar()
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
                    viewModel.fetchCharacters(pageNumber)
                }
            }


        })

        viewModel.fetchCharacters(pageNumber)
        viewModel.characterList.observe(viewLifecycleOwner) { character ->
            characterAdapter.addCharacters(character as ArrayList<CharacterDetailsData>)
            isLoading = false
            hideProgressBar()
        }

        characterAdapter.onItemClick = { chr ->
            val intent = Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra(Constant.chrId, chr.id)
            intent.putExtra(Constant.chrName, chr.attributes.name)
            intent.putExtra(Constant.chrImage, chr.attributes.image)
            intent.putExtra(Constant.chrSpecies, chr.attributes.species)
            intent.putExtra(Constant.chrGender, chr.attributes.gender)
            startActivity(intent)
        }
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        binding.characterRecycler.visibility = View.INVISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
        binding.characterRecycler.visibility = View.VISIBLE
    }
    
    
}