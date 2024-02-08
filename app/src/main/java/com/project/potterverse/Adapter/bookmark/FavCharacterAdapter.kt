package com.project.potterverse.Adapter.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.databinding.ItemCharacterBinding
import com.project.potterverse.databinding.ItemCharacterFragmentBinding
import com.project.potterverse.databinding.ItemFavBinding

class FavCharacterAdapter: RecyclerView.Adapter<FavCharacterAdapter.FavCharacterViewHolder>() {
    lateinit var onItemClick: ((CharacterDetailsData) -> Unit)

    private var charFavList = ArrayList<CharacterDetailsData>()
    fun setFavChars(charList: ArrayList<CharacterDetailsData>) {
        this.charFavList = charList as ArrayList<CharacterDetailsData>
        notifyDataSetChanged()
    }
    inner class FavCharacterViewHolder(val binding: ItemCharacterFragmentBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCharacterViewHolder {
        return FavCharacterViewHolder(ItemCharacterFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun getItemCount(): Int {
        return charFavList.size
    }

    override fun onBindViewHolder(holder: FavCharacterViewHolder, position: Int) {
        val currentCharacter = charFavList[position]

        if (currentCharacter.attributes.image != null) {
            Glide.with(holder.itemView)
                .load(currentCharacter.attributes.image)
                .into(holder.binding.characterImageView)
        } else {
            holder.binding.characterImageView.setImageResource(R.drawable.witchhat)
        }

        holder.binding.characterName.text = currentCharacter.attributes.name

        if (currentCharacter.attributes.species != null){
            holder.binding.species.text = currentCharacter.attributes.species
        } else {
            holder.binding.species.text = "unknown"
        }

        if (currentCharacter.attributes.gender != null){
            holder.binding.gender.text = currentCharacter.attributes.gender
        }  else {
            holder.binding.species.text = "unknown"
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentCharacter)
        }
    }
}