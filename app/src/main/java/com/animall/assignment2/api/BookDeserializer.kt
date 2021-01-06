package com.animall.assignment2.api

import android.content.ClipData
import com.animall.assignment2.data.entities.Book
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class BookDeserializer : JsonDeserializer<Book>  {
    override fun deserialize(json: JsonElement?, typeOfT: Type?,
                             context: JsonDeserializationContext?): Book {
        return getBook(json!!.asJsonObject)
    }

    fun getBook(bookJson : JsonObject) : Book{
        val id = bookJson["id"].asString
        val selfLink = bookJson["selfLink"].asString

        val volumeInfo = bookJson["volumeInfo"].asJsonObject

        var title = ""
        if(volumeInfo.has("title")) {
            title = volumeInfo["title"].asString
        }

        val authors: MutableList<String> = mutableListOf()
        if(volumeInfo.has("authors")) {
            val authorsArray = volumeInfo["authors"].asJsonArray
            for (item in authorsArray) {
                authors.add(item.asString)
            }
        }

        var publisher : String = ""
        if(volumeInfo.has("publisher")) {
            publisher = volumeInfo["publisher"].asString
        }

        var pageCount : Int = 0
        if(volumeInfo.has("pageCount")){
            pageCount = volumeInfo["pageCount"].asInt
        }

        var thumbnail = ""
        if(volumeInfo.has("imageLinks")) {
            val imageLinks = volumeInfo["imageLinks"].asJsonObject
            if(imageLinks.has("thumbnail")) {
                thumbnail = imageLinks["thumbnail"].asString
            }
        }

        return Book(id,selfLink,title,authors,publisher,pageCount,thumbnail)
    }
}