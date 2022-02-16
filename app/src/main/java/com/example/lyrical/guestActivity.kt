package com.example.lyrical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Button

class guestActivity: AppCompatActivity()  {

    private lateinit var voice: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guest)
         voice = findViewById<ImageButton>(R.id.voice)
    }
   private fun voiceToText(){


    }
}