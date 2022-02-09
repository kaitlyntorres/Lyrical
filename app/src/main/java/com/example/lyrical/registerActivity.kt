package com.example.lyrical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

//A toast is a view containing a quick little message for the user


class registerActivity: AppCompatActivity() {
    // intialize variables
    private lateinit var email: EditText
    private lateinit var passwd: EditText
    private lateinit var confirmpasswd: EditText
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        // find variable by id from xml
        email = findViewById(R.id.email)
        passwd = findViewById(R.id.password)
        confirmpasswd = findViewById(R.id.confirmpassword)

        btnRegister = findViewById(R.id.sumbitregister)
        // connect to firebase
        auth = Firebase.auth
        // on click call function
        btnRegister.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser() {
        // converts user input to string
        val email = email.text.toString()
        val password = passwd.text.toString()
        val confirmpassword = confirmpasswd.text.toString()

        if (email.isBlank()) {
            Toast.makeText(this, "Email is blank. Try again", Toast.LENGTH_SHORT).show()
            return
        } else if (password.isBlank() || confirmpassword.isBlank()) {
            Toast.makeText(this, "Password is  blank. Try again", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmpassword) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            return
        }


            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    // main menu
                    finish()
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()

                }
            }
        }


    }



