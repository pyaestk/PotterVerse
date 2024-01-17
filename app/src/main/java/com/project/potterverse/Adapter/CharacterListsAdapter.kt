package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.CharactersData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.ItemCharacterBinding

class CharacterListsAdapter: RecyclerView.Adapter<CharacterListsAdapter.CharacterListViewHolder>() {

    lateinit var onItemClick: ((CharactersData) -> Unit)

    private var characterList = ArrayList<CharactersData>()
    fun addCharacters(newCharacters: ArrayList<CharactersData>) {
        // Filter out duplicates by checking for unique IDs
        val uniqueNewCharacters = newCharacters.filterNot { newCharacter ->
            characterList.any { existingCharacter ->
                newCharacter.id == existingCharacter.id
            }
        }

        // Add the unique characters to the list
        characterList.addAll(uniqueNewCharacters)
        notifyDataSetChanged()
    }
    inner class CharacterListViewHolder(val binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        return CharacterListViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        if (characterList[position].attributes.image != null) {
            Glide.with(holder.itemView)
                .load(characterList[position].attributes.image)
                .into(holder.binding.characterImageView)
        } else {
            holder.binding.characterImageView.setImageResource(R.drawable.witchhat)
        }

        holder.binding.characterName.text = characterList[position].attributes.name

        if (characterList[position].attributes.species != null){
            holder.binding.species.text = characterList[position].attributes.species
        } else {
            holder.binding.species.text = "unknown"
        }

        if (characterList[position].attributes.gender != null){
            holder.binding.gender.text = characterList[position].attributes.gender
        }  else {
            holder.binding.species.text = "unknown"
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(characterList[position])
        }
    }
}