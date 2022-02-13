package com.example.lyrical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import com.google.android.material.snackbar.Snackbar
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Intent




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

    private fun registerUser(){
        // converts user input to string
        val e = email.text.toString()
        val password = passwd.text.toString()
        val confirmpassword = confirmpasswd.text.toString()
        // must have at least 1 uppercase, lowercase, number, special char , and be at least 8 char long
        val pattern ="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%?&])[A-Za-z0-9@$!%?&]{8,}$".toRegex()



        if (e.isEmpty()) {
            email.setError("Email can't be empty")
            return

            //Toast.makeText(this, "Email is blank. Try again", Toast.LENGTH_SHORT).show()

        } else if (password.isEmpty()) {
            passwd.setError("Password can't be blank")
            return


            //var builder= AlertDialog.Builder(activity)
           //Toast.makeText(this, "Password is  blank. Try again", Toast.LENGTH_SHORT).show()

        }
        else if (confirmpassword.isEmpty()) {
            confirmpasswd.setError("Must confirm a password")
            return


            //var builder= AlertDialog.Builder(activity)
            //Toast.makeText(this, "Password is  blank. Try again", Toast.LENGTH_SHORT).show()

        }


        if(!pattern.containsMatchIn(password))
        {
            passwd.setError("invalid password ")
            return
        }

        if (password != confirmpassword) {
            passwd.setError("passwords don't match!")
            confirmpasswd.setError("passwords don't match!")
            return

        }


            auth.createUserWithEmailAndPassword(e, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    // main menu
                    val i = Intent(this, guestActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()

                }
            }
        }


    }



