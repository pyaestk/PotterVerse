package com.project.potterverse.data

data class Attributes(
    val alias_names: List<String>,
    val animagus: String,
    val blood_status: String,
    val boggart: String,
    val born: String,
    val died: String,
    val eye_color: String,
    val family_members: List<String>,
    val gender: String,
    val hair_color: String,
    val height: String,
    val house: String,
    val image: String,
    val jobs: List<String>,
    val marital_status: String,
    val name: String,
    val nationality: String,
    val patronus: String,
    val romances: List<String>,
    val skin_color: String,
    val slug: String,
    val species: String,
    val titles: List<String>,
    val wands: List<String>,
    val weight: String,
    val wiki: String
)
data class CharactersData(
    val attributes: Attributes,
    val id: String,
    val links: Links,
    val type: String
)
data class CharactersList(
    val data: List<CharactersData>,
)