package com.example.lyrical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val button1 = findViewById<Button>(R.id.loginbutton)
        button1.setOnClickListener {
            val i = Intent(this, loginActivity::class.java)
            startActivity(i)
        }

        val button2 = findViewById<Button>(R.id.registerbutton)
        button2.setOnClickListener {
            val i = Intent(this, registerActivity::class.java)
            startActivity(i)
        }

        val button3 = findViewById<Button>(R.id.guestbutton)
        button3.setOnClickListener {
            val i = Intent(this, guestActivity::class.java)
            startActivity(i)
        }

        val button4 = findViewById<Button>(R.id.apibutton)
        button4.setOnClickListener {
            val i = Intent(this, ApiActivity::class.java)
            startActivity(i)
        }

    }
}