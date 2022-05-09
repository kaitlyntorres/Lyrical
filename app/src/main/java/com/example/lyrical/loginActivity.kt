package com.example.lyrical

/*
* Authors: Omer Basar, Kaitlyn Torres, Charles Howard
* File: loginActivity
* Purpose: allows users to login
*
* */

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


//A toast is a view containing a quick little message for the user


//LOGIN PAGE

//initialize attribute
    @SuppressLint("StaticFieldLeak")
    lateinit var email: EditText
    @SuppressLint("StaticFieldLeak")
    lateinit var passwd: EditText
    @SuppressLint("StaticFieldLeak")
    private lateinit var forgotb: TextView
    @SuppressLint("StaticFieldLeak")
    private lateinit var r: TextView
    @SuppressLint("StaticFieldLeak")
    private lateinit var loginbutton: Button
    @SuppressLint("StaticFieldLeak")
    private lateinit var guestbutton: Button




    lateinit var auth: FirebaseAuth
    class loginActivity: AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            getSupportActionBar()?.hide()
            setContentView(R.layout.loginpage)
            // reads in data from login xml by its id
            email = findViewById(R.id.email)
            passwd = findViewById(R.id.password)
            // connection to firebase
            auth = Firebase.auth


            forgotb = findViewById(R.id.forgotp)
            //on click sends to reset kotlin activity
            forgotb.setOnClickListener {
                val i = Intent(this, passwordReset::class.java)
                startActivity(i)
            }

            // on click goes to login function
            loginbutton = findViewById(R.id.submitbutton)
            loginbutton.setOnClickListener {
                login()
            }

            r=findViewById(R.id.register)
            r.setOnClickListener {
                val i = Intent(this, registerActivity::class.java)
                startActivity(i)

            }

            guestbutton= findViewById(R.id.guestbutton)
            guestbutton.setOnClickListener {
                val i = Intent(this, homeActivity::class.java)
                startActivity(i)
            }



        }


        private fun login() {


            // var sampleContext=loginActivity.getContext()
            // converts user input to string
            val e = email.text.toString()
            val password = passwd.text.toString()
            //checks if email is empty
            if (e.isEmpty()) {
                email.setError("Email can't be empty")
                return

                //checks if password is empty
            } else if (password.isBlank()) {
                passwd.setError("Password can't be empty")
                return
            }

            //calls signin method which checks if corresponding email and password are in firebase database
            auth.signInWithEmailAndPassword(e, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT)
                        .show()

                    // if login is successful, continues on to app
                    val i = Intent(this, homeActivity::class.java)
                    i.putExtra("Email",e)
                    startActivity(i)

                } else {

                    email.setError("Email or Password incorrect")
                    passwd.setError("Email or Password incorrect")
                }
            }
        }
    }







