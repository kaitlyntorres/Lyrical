package com.example.lyrical

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import java.util.*
import android.widget.ArrayAdapter


    class displaySong: AppCompatActivity() {
        //private lateinit var song: ListView
        //var array = arrayOf("Melbourne", "Vienna", "Vancouver")
        //val song_name = intent.getSerializableExtra("song") as List<SongModel?>
        val song_name: MutableList<SongModel>  = intent.getSerializableExtra("list") as MutableList<SongModel>
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            getSupportActionBar()?.hide()
            setContentView(R.layout.activity_api)
            val adapter = ArrayAdapter(this,R.layout.list_row, song_name)
            val song = findViewById<ListView>(R.id.listview)

            song.setAdapter(adapter)




        }
    }