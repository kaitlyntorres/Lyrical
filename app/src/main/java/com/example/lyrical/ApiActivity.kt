package com.example.lyrical

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.adamratzman.spotify.spotifyAppApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.*
import ru.gildor.coroutines.okhttp.await
import java.util.*

private val handler: Handler = Handler()

class ApiActivity : AppCompatActivity() {
    lateinit var loadingBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize all necessary stuff first
        setContentView(R.layout.waiting)

        val search = intent.getStringExtra("keyOne")
        super.onCreate(savedInstanceState)










        // Use Coroutine to allow usage of suspended functions
        GlobalScope.launch {

            // Get the songs
            var songs = identifyTheSongs(search)
            songs = includeSpotID(songs)
            songs = filterOutSongs(songs)

            // Convert to String array for sending through intent
            val array: Array<String> = songs.keys.map { songs[it].toString() }.toTypedArray()

            // Create intent and send data over to song player
            val it = Intent(this@ApiActivity, spotifySong::class.java)
            it.putExtra("keyTwo", array)
            startActivity(it)
        }

    }

    private suspend fun identifyTheSongs(url: String?): HashMap<String, songObject> {
        //Initialize attributes
        val apiKey = "19df70b8a3msh1772cc59042fda4p151eb0jsnc1008177d29a"
        val songMap = hashMapOf<String, songObject>()
        var k = 0

        // Take in search parameter and customize it to fit into search url
        val temp = url?.replace(" ", "%20")

        // Create client, request, and make the call to Genius
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url("https://genius.p.rapidapi.com/search?q=$temp")
            .get()
            .addHeader("x-rapidapi-host", "genius.p.rapidapi.com")
            .addHeader("x-rapidapi-key", apiKey)
            .build()
        val result = client.newCall(request).await()

        // Retrieve the result and convert the entries into
        val body = result.body?.string()
        val responseObj = JsonParser.parseString(body)
        val gson = GsonBuilder().create()
        val songMatches =
            responseObj.asJsonObject.get("response").asJsonObject.get("hits").asJsonArray

        //For each song in the Json array, convert it to a songObject
        for (song in songMatches) {
            // Convert song to a SongModel so it can be read normally
            val songInfo = song.asJsonObject.get("result")
            val song1 = gson.fromJson(songInfo, SongModel::class.java)

            // Create parameters for songObject constructor
            val title = song1.title
            val artist = song1.artist_names
            val lyrics = song1.lyrics_state
            val full = song1.full_title

            // Create new songObject and add it to the hashmap
            val newSong = songObject(title, artist, lyrics, full, "")
            songMap["$k"] = newSong

            // Increment key count by one
            k += 1
        }

        // Return the hashmap
        return songMap
    }

    private suspend fun includeSpotID(songMap: HashMap<String, songObject>): HashMap<String, songObject> {
        // Iterate through each song in the hashmap
        for ((key, value) in songMap) {
            // Search for and retrieve the Spotify track ID
            val search = (value.getTitle() + " " + value.getArtist())
            val trackID = findSpotID(search)

            // Update the songObject and reflect the changes in the hashmap
            value.setTrack(trackID)
            songMap[key] = value
        }

        // Return the updated hashmap
        return songMap
    }

    private suspend fun findSpotID(search: String): String {
        // Used for making request with Spotify API
        val id = "8d9e2925b2f54fa295aa5830f014e7f4"
        val secret = "f86fb3ad97524cd9a480533e00c6964a"

        // Create API protocol and make request
        val api = spotifyAppApi(id, secret).build()
        val spotifyUrl = api.search.searchTrack(search)

        // Convert the url to a string and filter out the track ID
        var spotID = spotifyUrl.toString()
        spotID = spotID.substringAfter("https://open.spotify.com/track/").substringBefore('}')

        // Return the track ID
        return spotID
    }

    private fun filterOutSongs(songMap: HashMap<String, songObject>): HashMap<String, songObject> {
        // Initialize attributes
        val indexList = mutableListOf<String>()

        // Find songs with that have paging object instead of a track id
        for ((K, V) in songMap) {
            if (V.spotify_id.startsWith("PagingObject")) {
                indexList.add(K)
            }
        }

        // Remove those specific songs from the hashmap
        for (i in indexList) {
            songMap.remove(i)
        }

        // Return the filtered hashmap
        return songMap
    }
}





