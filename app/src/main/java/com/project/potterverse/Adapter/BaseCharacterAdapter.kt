package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.databinding.ItemCharacterBinding
import com.project.potterverse.databinding.ItemCharacterFragmentBinding

class BaseCharacterAdapter(var useFragmentBinding: Boolean): RecyclerView.Adapter<BaseCharacterAdapter.BaseCharacterViewHolder>() {

    lateinit var onItemClick: ((CharacterDetailsData) -> Unit)
    private var characterList = ArrayList<CharacterDetailsData>()
    fun addCharacters(newCharacters: ArrayList<CharacterDetailsData>) {
        val uniqueNewCharacters = newCharacters.filterNot { newCharacter ->
            characterList.any { existingCharacter ->
                newCharacter.id == existingCharacter.id
            }
        }
        characterList.addAll(uniqueNewCharacters)
        notifyDataSetChanged()
    }
    inner class BaseCharacterViewHolder(val binding: ViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCharacterViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val binding = if (useFragmentBinding) {
            ItemCharacterFragmentBinding.inflate(inflater, parent, false)
        } else {
            ItemCharacterBinding.inflate(inflater, parent, false)
        }
        return BaseCharacterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: BaseCharacterViewHolder, position: Int) {
        var currentCharacter = characterList[position]

        when(val binding = holder.binding) {
            is ItemCharacterBinding -> {
                if (currentCharacter.attributes.image != null) {
                    Glide.with(holder.itemView)
                        .load(characterList[position].attributes.image)
                        .into(binding.characterImageView)
                } else {
                    binding.characterImageView.setImageResource(R.drawable.witchhat)
                }

                binding.characterName.text = characterList[position].attributes.name

                if (currentCharacter.attributes.species != null){
                    binding.species.text = characterList[position].attributes.species
                } else {
                    binding.species.text = "unknown"
                }

                if (currentCharacter.attributes.gender != null){
                    binding.gender.text = characterList[position].attributes.gender
                }  else {
                    binding.species.text = "unknown"
                }
            }
            is ItemCharacterFragmentBinding -> {
                if (currentCharacter.attributes.image != null) {
                    Glide.with(holder.itemView)
                        .load(characterList[position].attributes.image)
                        .into(binding.characterImageView)
                } else {
                    binding.characterImageView.setImageResource(R.drawable.witchhat)
                }

                binding.characterName.text = characterList[position].attributes.name

                if (currentCharacter.attributes.species != null){
                    binding.species.text = characterList[position].attributes.species
                } else {
                    binding.species.text = "unknown"
                }

                if (currentCharacter.attributes.gender != null){
                    binding.gender.text = characterList[position].attributes.gender
                }  else {
                    binding.species.text = "unknown"
                }

            }
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(characterList[position])
        }
    }
}