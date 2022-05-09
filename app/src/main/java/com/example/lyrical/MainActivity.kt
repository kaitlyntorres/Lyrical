package com.example.lyrical

/*
* Authors: Omer Basar, Kaitlyn Torres, Charles Howard
* File: MainActivity
* Purpose: it is the welcome page
*
* */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.view.Window
import android.view.WindowManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getSupportActionBar()?.hide()
        setContentView(R.layout.main)
        val button1 = findViewById<Button>(R.id.sbutton)

        button1.setOnClickListener {
            val i = Intent(this, loginActivity::class.java)
            startActivity(i)
        }

    }
}