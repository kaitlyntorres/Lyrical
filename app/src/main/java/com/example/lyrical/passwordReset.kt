package com.example.lyrical

/*
* Authors: Omer Basar, Kaitlyn Torres, Charles Howard
* File: passwordReset
* Purpose: allows users to reset their password
*
* */

import android.content.DialogInterface
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


//is called if user clicks forgot password button


class passwordReset: AppCompatActivity() {
    //initialize attributes
    private lateinit var e: EditText
    private lateinit var btnReset: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getSupportActionBar()?.hide()
        setContentView(R.layout.forgot)
        e = findViewById(R.id.email)
        btnReset = findViewById(R.id.forgotp)
        auth = Firebase.auth

        //user enters email and then clicks button to receive an email to change password

        //calls f function when button is clicked
        btnReset.setOnClickListener {
            f()

        }
    }
    private fun f(){
        val eforgot = e.text.toString()
        //if email is empty show error
        if (eforgot.isEmpty()) {
            e.setError("Email can't be empty")
        } else {
           // sends email to user only if email is valid (in database)
            auth.sendPasswordResetEmail(eforgot)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {

                        try {
                            throw task.exception!!
                        } // if user enters wrong email.
                        catch (invalidEmail: FirebaseAuthInvalidUserException) {
                            e.setError("Invalid email")
                        }

                    } else {
                        val dialog = AlertDialog.Builder(this)
                        //sends message that email is sent
                        dialog.setMessage("Email sent")
                            .setCancelable(false)
                                //brings back to main page
                            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                                    dialog, id -> finish()
                            })
                        val alertdialog = dialog.create()

                        alertdialog.show()
                    }
                }
        }
    }
}
