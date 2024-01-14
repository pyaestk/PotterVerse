package com.project.potterverse.data

data class Relationships(
    val chapters: Chapters
)
data class Links(
    val self: String
)
data class DataX(
    val id: String,
    val type: String
)
data class Chapters(
    val data: List<DataX>
)
data class BookAttributes(
    val author: String,
    val cover: String,
    val dedication: String,
    val pages: Int,
    val release_date: String,
    val slug: String,
    val summary: String,
    val title: String,
    val wiki: String
)

data class BookData(
    val attributes: BookAttributes,
    val id: String,
    val links: Links,
    val relationships: Relationships,
    val type: String
)

data class BooksList(
    val data: List<BookData>,
)