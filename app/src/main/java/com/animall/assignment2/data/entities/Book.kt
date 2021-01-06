package com.animall.assignment2.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books")
data class Book (
        @PrimaryKey
        @SerializedName("id")
        val id: String,
        @SerializedName("selfLink")
        val selfLink: String,
        /*@Embedded
        @SerializedName("volumeInfo")
        val volumeInfo: VolumeInfo*/
        @SerializedName("title")
        val title: String,
        @SerializedName("authors")
        val authors: List<String> = listOf(),
        @SerializedName("publisher")
        val publisher: String,
        @SerializedName("pageCount")
        val pageCount: Int?,
        /*@Embedded
        @SerializedName("imageLinks")
        val imageLinks: ImageLinks,*/
        @SerializedName("thumbnail")
        val thumbnail: String
)


