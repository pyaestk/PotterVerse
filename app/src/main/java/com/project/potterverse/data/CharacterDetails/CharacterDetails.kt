package com.project.potterverse.data.CharacterDetails

import androidx.room.Entity
import androidx.room.PrimaryKey


data class CharacterDetailsAttributes(
    val alias_names: List<String>?,
    val animagus: Any?,
    val blood_status: String?,
    val boggart: String?,
    val born: String?,
    val died: Any?,
    val eye_color: String?,
    val family_members: List<String>?,
    val gender: String?,
    val hair_color: String?,
    val height: Any?,
    val house: String?,
    val image: String?,
    val jobs: List<String>?,
    val marital_status: String?,
    val name: String?,
    val nationality: String?,
    val patronus: String?,
    val romances: List<String>?,
    val skin_color: String?,
    val slug: String?,
    val species: String?,
    val titles: List<String>?,
    val wands: List<String>?,
    val weight: Any?,
    val wiki: String?
)
@Entity("characterInformation")
data class CharacterDetailsData(
    val attributes: CharacterDetailsAttributes,
    @PrimaryKey
    val id: String,
    val type: String,
    var bookmark: Boolean
)
data class CharacterDetails(
    val data: CharacterDetailsData,
)

data class CharactersList(
    val data: List<CharacterDetailsData>
)