package com.project.potterverse.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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

data class BookDetailsAttributes(
    val author: String?,
    val cover: String?,
    val dedication: String?,
    val pages: Int?,
    val release_date: String?,
    val slug: String?,
    val summary: String?,
    val title: String?,
    val wiki: String?
)

@Entity("bookInformation")
data class BookDetailsData(
    val attributes: BookDetailsAttributes,
    @PrimaryKey
    val id: String,
    val relationships: BookDetailsRelationships,
    val type: String,
    var bookmark: Boolean,
)
data class BookDetails(
    val data: BookDetailsData,
)

data class BooksList(
    val data: List<BookDetailsData>,
)
