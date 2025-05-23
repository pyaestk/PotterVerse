package com.project.potterverse.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.databinding.ActivityCharacterDetailsBinding
import com.project.potterverse.model.CharacterDetailsData
import com.project.potterverse.utils.Constant
import com.project.potterverse.view.viewModel.CharacterDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding
    private val viewModel: CharacterDetailsViewModel by viewModel()

    private lateinit var chrId: String
    private var chrImg: String ?= null
    private lateinit var chrLink: String

    private var isSaved: Boolean? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showProgressBar()

        val intent = intent
        chrId = intent.getStringExtra(Constant.chrId)!!
        chrImg = intent.getStringExtra(Constant.chrImage)
        binding.chrName.text = intent.getStringExtra(Constant.chrName)
        binding.speciesTextView.text = intent.getStringExtra(Constant.chrSpecies)
        binding.genderTextView.text = intent.getStringExtra(Constant.chrGender)

        if (chrImg == null) {
            binding.chrImage.setImageResource(R.drawable.witchhat)
        } else {
            Glide.with(applicationContext).load(chrImg).into(binding.chrImage)
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.linkbutton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(chrLink)))
        }

        viewModel.fetchCharacterDetails(chrId)
        observerCharacterDetails()
        onBookmarkedClick()
    }


    private var charToSave: CharacterDetailsData? = null
    private fun observerCharacterDetails() {

        viewModel.getCharacterDetails().observe(this) { chr ->
            charToSave = chr
            observingDetails(chr)
            hideProgressBar()
        }
        viewModel.getAllFavChar().observe(this) { charList ->
            isSaved = charList.any { it.id == chrId }
            if (isSaved == true) {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmarked)
            } else {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border)
            }
            onBookmarkedClick()
        }

    }

    private fun onBookmarkedClick() {
        binding.btnBookmark.setOnClickListener {
            charToSave?.let { char ->
                if (isSaved == true){
                    viewModel.deleteChar(char)
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border)
                } else {
                    char.bookmark = false
                    viewModel.insertChar(char)
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmarked)
                }
            }
        }
    }

    private fun observingDetails(chr: CharacterDetailsData) {
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

        chrLink = chr.attributes.wiki!!
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