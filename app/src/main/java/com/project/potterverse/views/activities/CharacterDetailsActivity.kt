package com.project.potterverse.views.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.databinding.ActivityCharacterDetailsBinding
import com.project.potterverse.viewModel.CharacterDetailsViewModel
import com.project.potterverse.views.fragments.homeFragment
import com.project.potterverse.views.fragments.homeFragment.Companion.bookImage

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding
    private lateinit var viewModel: CharacterDetailsViewModel

    private lateinit var chrId: String
    private var chrImg: String ?= null
    private lateinit var chrLink: String
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showProgressBar()
        viewModel = ViewModelProvider(this)[CharacterDetailsViewModel::class.java]

        val intent = intent
        chrId = intent.getStringExtra(homeFragment.chrId)!!
        chrImg = intent.getStringExtra(homeFragment.chrImage)
        binding.chrName.text = intent.getStringExtra(homeFragment.chrName)
        binding.speciesTextView.text = intent.getStringExtra(homeFragment.chrSpecies)
        binding.genderTextView.text = intent.getStringExtra(homeFragment.chrGender)

        if (chrImg == null) {
            binding.chrImage.setImageResource(R.drawable.witchhat)
        } else {
            Glide.with(applicationContext).load(chrImg).into(binding.chrImage)
        }
        viewModel.fetchCharacterDetails(chrId)
        observerCharacterDetails()

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.linkbutton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(chrLink)))
        }
    }

    private fun observerCharacterDetails() {

        viewModel.getCharacterDetails().observe(this) { chr ->
            hideProgressBar()

            if (!chr.attributes.born.isNullOrEmpty()) {
                binding.bornTextView.visibility = View.VISIBLE
                binding.bornTextView.text = "Born: ${chr.attributes.born}"
            }

            if (!chr.attributes.nationality.isNullOrEmpty()) {
                binding.nationalityTextView.visibility = View.VISIBLE
                binding.nationalityTextView.text = "Nationality: ${chr.attributes.nationality}"
            }

            if (!chr.attributes.marital_status.isNullOrEmpty()) {
                binding.maritalStatusTextView.visibility = View.VISIBLE
                binding.maritalStatusTextView.text = "Marital Status: ${chr.attributes.marital_status}"
            }

            if (!chr.attributes.blood_status.isNullOrEmpty()) {
                binding.bloodTextView.visibility = View.VISIBLE
                binding.bloodTextView.text = "Blood Type: ${chr.attributes.blood_status}"
            }

            if (!chr.attributes.house.isNullOrEmpty()) {
                binding.houseTextView.visibility = View.VISIBLE
                binding.houseTextView.text = "House: ${chr.attributes.house}"
            }

            if (!chr.attributes.eye_color.isNullOrEmpty()) {
                binding.eyeColorTextView.visibility = View.VISIBLE
                binding.eyeColorTextView.text = "Eye color: ${chr.attributes.eye_color}"
            }

            if (!chr.attributes.hair_color.isNullOrEmpty()) {
                binding.hairTextView.visibility = View.VISIBLE
                binding.hairTextView.text = "Hair color: ${chr.attributes.hair_color}"
            }

            if (!chr.attributes.boggart.isNullOrEmpty()) {
                binding.boggartTextView.visibility = View.VISIBLE
                binding.boggartTextView.text = "Boggard: ${chr.attributes.boggart}"
            }

            if (!chr.attributes.patronus.isNullOrEmpty()) {
                binding.patronusTextView.visibility = View.VISIBLE
                binding.patronusTextView.text = "Patronus: ${chr.attributes.patronus}"
            }

            chrLink = chr.attributes.wiki

        }

    }

    private fun hideProgressBar() {
        binding.moreInfo.visibility = View.VISIBLE
        binding.progressBar3.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.moreInfo.visibility = View.GONE
        binding.progressBar3.visibility = View.VISIBLE
    }
}