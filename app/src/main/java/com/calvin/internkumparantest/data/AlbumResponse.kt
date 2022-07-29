package com.calvin.internkumparantest.data

import com.google.gson.annotations.SerializedName

data class AlbumResponseItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("userId")
    val userId: Int
)
