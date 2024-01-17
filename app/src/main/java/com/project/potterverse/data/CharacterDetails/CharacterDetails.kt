package com.project.potterverse.data.CharacterDetails

data class Meta(
    val copyright: String,
    val generated_at: String
)
data class LinksX(
    val self: String
)
data class CharacterDetailsData(
    val attributes: CharacterDetailsAttributes,
    val id: String,
    val links: LinksX,
    val type: String
)
data class CharacterDetailsAttributes(
    val alias_names: List<String>,
    val animagus: Any,
    val blood_status: String,
    val boggart: String,
    val born: String,
    val died: Any,
    val eye_color: String,
    val family_members: List<String>,
    val gender: String,
    val hair_color: String,
    val height: Any,
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
    val weight: Any,
    val wiki: String
)
data class CharacterDetails(
    val data: CharacterDetailsData,
    val links: LinksX,
    val meta: Meta
)