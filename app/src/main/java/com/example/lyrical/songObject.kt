package com.example.lyrical

/*
* Authors: Omer Basar, Kaitlyn Torres, Charles Howard
* File: songObject
* Purpose: allows storage and usage of data from genius api call
*
* */

class songObject (
    var song_title : String,
    var song_artist : String,
    var song_lyrics : String,
    var song_full_title : String,
    var spotify_id : String
    ){
    fun getTitle():String{
        return song_title
    }
    fun getArtist():String{
        return song_artist
    }
    fun getLyrics():String{
        return song_lyrics
    }
    fun getFull():String{
        return song_full_title
    }
    fun getTrack():String{
        return spotify_id
    }
    fun setTrack(sample:String){
        spotify_id = sample
    }
    override fun toString():String
    {
        return "Full Title: " + song_full_title + ", TrackID: " + spotify_id
    }
}






