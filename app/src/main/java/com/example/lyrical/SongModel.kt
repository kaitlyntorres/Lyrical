package com.example.lyrical

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongModel ( @SerializedName("annotation_count") val annotation_count: Int,
                       @SerializedName("api_path") val api_path: String,
                       @SerializedName("artist_names") val artist_names: String,
                       @SerializedName("full_title") val full_title: String,
                       @SerializedName("header_image_thumbnail_url") val header_image_thumbnail_url: String,
                       @SerializedName("header_image_url") val header_image_url: String,
                       @SerializedName("id") val id: Int,
                       @SerializedName("lyrics_owner_id") val lyrics_owner_id: Int,
                       @SerializedName("lyrics_state") val lyrics_state: String,
                       @SerializedName("path") val path: String,
                       @SerializedName("pyongs_count") val pyongs_count: Int,
                       @SerializedName("song_art_image_thumbnail_url") val song_art_image_thumbnail_url: String,
                       @SerializedName("song_art_image_url") val song_art_image_url: String,
                       @SerializedName("title") val title: String,
                       @SerializedName("title_with_featured") val title_with_featured: String,
                       @SerializedName("url") val url: String,
                     )
