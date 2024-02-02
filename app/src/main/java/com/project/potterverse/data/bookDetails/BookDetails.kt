package com.project.potterverse.data.bookDetails

data class BookDetailsDataX(
    val id: String,
    val type: String
)
data class BookDetailsRelationships(
    val chapters: BookDetailsChapters
)
data class BookDetailsChapters(
    val data: List<BookDetailsDataX>
)

data class Attributes(
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
data class BookDetailsData(
    val attributes: Attributes,
    val id: String,
    val relationships: BookDetailsRelationships,
    val type: String
)
data class BookDetails(
    val data: BookDetailsData,
)

data class BooksList(
    val data: List<BookDetailsData>,
)
